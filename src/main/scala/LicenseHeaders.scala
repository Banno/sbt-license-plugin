import sbt._
import java.io.File

trait LicenseHeaders extends BasicScalaProject {
  lazy val formatLicenseHeaders = task {
    mainSources.get.foldLeft(None: Option[String]) { (result, path) =>
      result orElse {
        val Right(fileContents) = FileUtilities.readString(path.asFile, log)
        
        if (alreadyHasHeader(fileContents))
          None
        else
          addHeader(path, fileContents, log)
      }
    }
  }

  def licenseText: String


  lazy val commentedLicenseTextLines: List[String] = {
    val commentedLines = licenseText.lines.map { line => " * " + line }.toList
    ("/*" :: commentedLines ::: " */" :: Nil)
  }

  private val lineSeparator = System.getProperty("line.separator")
  
  private def addHeader(path: Path, fileContents: String, log: Logger): Option[String] = {
    val destination = new File(path.projectRelativePath + ".withHeader")
    
    FileUtilities.append(destination,
                         commentedLicenseTextLines.mkString(lineSeparator),
                         log) orElse
    FileUtilities.append(destination, lineSeparator, log) orElse
    FileUtilities.append(destination, removeExistingHeaderBlock(fileContents), log) orElse
    FileUtilities.copyFile(destination, path.asFile, log)
  }
  
  private def alreadyHasHeader(fileContents: String): Boolean =
    fileContents.lines.toList.zip(commentedLicenseTextLines) forall {
      case (fileLine, commentLine) => fileLine == commentLine
    }

  private def removeExistingHeaderBlock(fileContents: String): String = {
    fileContents.lines.dropWhile { line =>
      line.startsWith("/*") || 
      line.startsWith(" *")
    } mkString(lineSeparator)
  }
}
