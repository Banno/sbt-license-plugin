sbtPlugin := true

name := "sbt-license-plugin"

organization := "com.banno"

version := "0.1.5"

scalaVersion := "2.10.5"

scalacOptions ++= Seq("-deprecation", "-unchecked")

scriptedSettings

bintrayOrganization := Some("banno")

bintrayRepository := "oss"

bintrayPackageLabels := Seq("sbt")

licenses ++= Seq(("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html")))
