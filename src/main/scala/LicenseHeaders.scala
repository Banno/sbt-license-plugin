import sbt._
import java.io.File

trait LicenseHeaders extends BasicScalaProject {
  lazy val formatLicenseHeaders = task {
    mainSources.get.foldLeft(None: Option[String]) { (result, path) =>
      val Right(fileContents) = FileUtilities.readBytes(path.asFile, log)
      val destination = new File(path.projectRelativePath + ".withHeader")
      result orElse {
        FileUtilities.append(destination, commentedLicenseText, log)
      } orElse {
        FileUtilities.append(destination, lineSeparator, log)
      } orElse {
        FileUtilities.append(destination, fileContents, log)
      } orElse {
        FileUtilities.copyFile(destination, path.asFile, log)
      }
    }
  }
  
  def licenseText: String

  def commentedLicenseText: String = {
    val commentedLines = licenseText.lines.map { line => " * " + line }.toList
    ("/*" :: commentedLines ::: " */" :: Nil) mkString (lineSeparator)
  }

  private val lineSeparator = System.getProperty("line.separator")
  
}
