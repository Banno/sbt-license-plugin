sbt-license-plugin
==========
This is a [simple-build-tool](http://simple-build-tool.googlecode.com/) plugin for inserting/updating license headers in your source files. It was developed for ease of use and general laziness.

Usage
-----
Mixing in the `LicenseHeaders` trait will give you a `format-license-headers` action in sbt. Invoking it will add the licenseText to all of your main sources files. If there is already a source license header (this is considered a block comment before the first scala statement), it will be replaced. This can be overriden by overriding the `removeExistingHeaderBlock` boolean method.

There are also `MITLicense` and `ApacheLicense2` traits for applying each of their respective licenses to the file. Both of these traits require you to implement a copyrightLine method in your project for the first line of the source license header.

It can be part of your regular compile cycle by defining the `compile` action as:

    override def compileAction = super.compileAction dependsOn formatLicenseHeaders

Installation
------------
1. Add the sbt-license-plugin to your plugin definition like so:
   
     i.e. in `project/plugins/Plugins.scala`:
     
         import sbt._
         
         class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
           val licensePlugin = "com.banno" % "sbt-license-plugin" % "0.0.1" from "https://github.com/downloads/T8Webware/sbt-license-plugin/sbt-license-plugin-0.0.1.jar"
         }

     Read more about plugins [here](http://code.google.com/p/simple-build-tool/wiki/SbtPlugins)
2. Mixin the `LicenseHeaders` trait into your `Project.scala` file and define `licenseText` as a string:

     i.e. in `project/build/Project.scala`:
     
         class Project(info: ProjectInfo) extends DefaultProject(info) with LicenseHeaders {
           def licenseText = "Copyright (c) Initech Corp. 2011\nJust don't sue us."
         }

Contributing
------------
Fork away, commit, and send a pull request. Make sure that the tests pass before you submit your pull request.

### Copyright (C) T8 Webware 2011
