import java.io.File._
import scala.io.Source
import com.banno.license._
import sbt._
import Keys._

object checkForLicenseBuild extends Build {

  val checkForLicenseSettings = Defaults.defaultSettings ++ Seq(
    name         := "add-license-already-there",
    version      := "1.0"
  )

  val checkForLicense = InputKey[Unit]("checkForLicense", "Removing Existing License Block if Already There")

  lazy val project = Project (
    "project",
    file ("."),
    settings = checkForLicenseSettings ++ Seq(checkForLicense <<= checkForLicenseInputTask)
  )  

  private def checkForLicenseInputTask = inputTask { (argTask: TaskKey[Seq[String]]) =>
      // Here, we map the argument task `argTask`
      (argTask, streams, scalaSource in Compile) map { (args: Seq[String], out, sourceDir) =>
        println("The arguments were:")
        args foreach println
        checkSources(sourceDir, out.log)
      }
    }
 
  private def checkSources(sourceDir: File, log: Logger) = {  
    (sourceDir ** "*.scala").get foreach { file =>
      println("file: " + file)
     val fileContents = IO.read(file)
      println("file: " + fileContents)

        val actualLines = Source.fromFile(file).getLines.map(_.stripLineEnd).toList
          println("ACTUAL LINES: " + actualLines)

        val licenseLines = licenseText.split("\n").map(line => " * " + line).toList

        val expectedLines = "/*" :: licenseLines ::: " */" :: "package something" :: Nil
          println("EXPECTDLINES: " + expectedLines)

        expectedLines.zip(actualLines).foldLeft(None: Option[String]) { (result, lines) =>
          val (expected, actual) = lines
          
 	    println("result: " + result)

          result orElse {
            if (expected == actual)
              None
            else
              Some(error("Expected:\n\t'%s'\nFound: \n\t'%s'" format (expected, actual)).toString)
          }
      }
    }
  }
 
  def licenseText = Licenses.apache2("Copyright 2013 T8 Webware")

}
