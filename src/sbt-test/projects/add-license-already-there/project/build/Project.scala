import sbt._
import java.io.File
import scala.io.Source

class Projct(info: ProjectInfo) extends DefaultProject(info) with LicenseHeaders {
  lazy val checkForLicense = task { args =>
    val path = args(0)
    log.warn("actual =====")
    val Right(contents) = FileUtilities.readString(new File(path), log)
    log.warn(contents)                               
    log.warn("=====")                               
    task {
      val source = Source.fromFile(new File(path))
      val actualLines = source.getLines.map(_.stripLineEnd).toList
      val licenseLines = licenseText.lines.map(line => " * " + line).toList
      val expectedLines = "/*" :: licenseLines ::: " */" :: "package something" :: Nil
      expectedLines.zip(actualLines).foldLeft(None: Option[String]) { (result, lines) =>
        val (expected, actual) = lines
        result orElse {
          if (expected == actual)
            None
          else
            Some("Expected:\n\t'%s'\nbut was \n\t'%s'" format (expected, actual))
        }
      }
    }                              
  }

  def licenseText = """
Copyright (c) Initech Corp. 2011

Licensed under your mom.
Not really.
Just don't sue us.
"""
}
