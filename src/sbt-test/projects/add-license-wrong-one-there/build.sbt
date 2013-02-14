import com.banno._
import license._
import scala.io._

Plugin.licenseSettings

LicenseKeys.license := Licenses.apache2("Copyright 2011 T8 Webware")

LicenseKeys.removeExistingHeaderBlock := true

