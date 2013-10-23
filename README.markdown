sbt-license-plugin
==========
This is a [simple-build-tool](http://www.scala-sbt.org/) plugin for inserting/updating license headers in your source files. It was developed for ease of use and general laziness.
Installation
------------
`sbt-license-plugin` for sbt 0.11.3+ is not yet published anywhere. Sorry. The git repository must be cloned locally and a `sbt publish-local` must be given.

Add the `sbt-license-plugin` to your project's (or .sbt's) plugin definition, like so:
   
   i.e. in `project/plugins.sbt`:
     
         addSbtPlugin("com.banno" % "sbt-license-plugin" % "0.0.4")
         
         resolvers += Resolver.file("Local Ivy Repository", file("/home/USER/.ivy2/local/"))(Resolver.ivyStylePatterns)

Read more about plugins [here](http://www.scala-sbt.org/release/docs/Extending/Plugins.html) and [here](https://github.com/mads379/sbt-plugin-examples). 
Usage
-----
`formatLicenseHeaders` is a task that is called from the sbt console. In the default `LicenseSettings`, it is part of the normal compile cycle.

1. To get the `formatLicenseHeaders` task that adds customizable header text, import the plugin's keys, and add the `LicenseSettings` setting to your build definition. Invoking `formatLicenseHeaders` will add the license to all of your main sources files. Optionally, you can remove any pre-existing header by setting the `removeExistingHeaderBlock` setting to `true`.

   i.e. in `build.sbt`:
     
         import com.banno.license.Plugin.LicenseKeys._
         
         licenseSettings
         
         removeExistingHeaderBlock := true
         
         name := "example"
         
         version := "0.1"
         
         organization := "com.banno"
2. To add an `apache2` or `mit` license with a custom copyright line, import the licenses as well and set the `license` setting like so: 

   i.e. in `build.sbt`:
     
         import com.banno.license.Plugin.LicenseKeys._
         import com.banno.license.Licenses._
         
         licenseSettings

         license := apache2("Copyright 2013")

         removeExistingHeaderBlock := true
         
         name := "example"
         
         version := "0.1"
         
         organization := "com.banno"

Testing
------------
Ensure that things are working properly by testing the plugin with the `scripted` task, however, note that the scripted-plugin only works with sbt launcher versions 0.12-0.12.3. Learn more about testing plugins [here](http://eed3si9n.com/testing-sbt-plugins).

Contributing
------------
Fork away, commit, and send a pull request. Make sure that the tests pass before you submit your pull request.

### Copyright (C) T8 Webware 2011-2013
