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
          addHeader(path, fileContents)
      }
    }
  } describedAs ("Adds license header to all source files.")

  def licenseText: String


  private lazy val commentedLicenseTextLines: List[String] = {
    val commentedLines = licenseText.lines.map { line => " * " + line }.toList
    ("/*" :: commentedLines ::: " */" :: Nil)
  }

  private val lineSeparator = System.getProperty("line.separator")
  
  private def addHeader(path: Path, fileContents: String): Option[String] = {
    val withHeader = new File(path.projectRelativePath + ".withHeader")
    log.info("Adding license header to source file: " + path)

    FileUtilities.append(withHeader,
                         commentedLicenseTextLines.mkString(lineSeparator),
                         log) orElse
    FileUtilities.append(withHeader, lineSeparator, log) orElse
    FileUtilities.append(withHeader, removeExistingHeaderBlock(fileContents), log) orElse
    FileUtilities.copyFile(withHeader, path.asFile, log) orElse {
      if (withHeader.delete) None
      else Some("Unable to delete " + withHeader)
    }
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
