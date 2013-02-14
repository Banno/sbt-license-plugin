package com.banno
package license

import sbt._
import sbt.Keys._

object Plugin extends sbt.Plugin {
  import LicenseKeys._
  
  object LicenseKeys {  
    lazy val formatLicenseHeaders = TaskKey[Unit]("formatLicenseHeaders", "Includes the license header to source files")
    lazy val license = SettingKey[String]("license", "The license text to use")
    lazy val removeExistingHeaderBlock = SettingKey[Boolean]("removeExistingHeaderBlock", "Removes existing header blocks")
  }  
  
  def licenseSettings = Seq(
    license := "Replace this with your license text!",
    removeExistingHeaderBlock := false,
    formatLicenseHeaders <<= formatLicenseHeadersTask,
    compileInputs in Compile <<= compileInputs in Compile dependsOn formatLicenseHeaders
  )  
  
  private val lineSeparator = System.getProperty("line.separator")
  
  private def addHeader(path: File, fileContents: String, header: List[String], 
    removeExistingHeader: Boolean, log: Logger) {
      
    val withHeader = new File(path + ".withHeader")
    log.info("Adding license header to source file: " + path)

    IO.append(withHeader, header.mkString(lineSeparator))
    IO.append(withHeader, lineSeparator)
    IO.append(withHeader,
      if (removeExistingHeader) 
        withoutExistingHeaderBlock(fileContents)
      else
        fileContents)   
    
    IO.copyFile(withHeader, path.asFile) 

    if (! withHeader.delete) 
      log.error("Unable to delete " + withHeader)
  } 
  
  private def commentedLicenseTextLines(licenseText: String): List[String] = {
    val commentedLines = licenseText.split('\n').map { line => " * " + line }.toList
    ("/*" :: commentedLines ::: " */" :: Nil)
  }    
  
  private def alreadyHasHeader(fileContents: String, header: List[String]): Boolean =
    fileContents.split(lineSeparator).zip(header) forall {
      case (fileLine, commentLine) => fileLine == commentLine
    }   
    
  private def withoutExistingHeaderBlock(fileContents: String): String = {
    fileContents.split(lineSeparator).dropWhile { line =>
      line.startsWith("/*") || 
      line.startsWith(" *")
    } mkString(lineSeparator)
  }
  
  private def modifySources(sourceDir: File, licenseText: String, 
    removeExistingHeaders: Boolean, log: Logger) = {
      
    val header = commentedLicenseTextLines(licenseText)
    
    (sourceDir ** "*.scala").get foreach { path =>
      val fileContents = IO.read(path)
            
      if(! alreadyHasHeader(fileContents, header))
        addHeader(path, fileContents, header, removeExistingHeaders, log)
    }
  }
  
  private def formatLicenseHeadersTask = 
    (streams, scalaSource in Compile, license in formatLicenseHeaders, removeExistingHeaderBlock in formatLicenseHeaders) map {
      (out, sourceDir, lic, removeHeader) =>
        modifySources(sourceDir, lic, removeHeader, out.log)
    } 
    
  
}
