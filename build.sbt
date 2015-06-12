import bintray.Keys._

sbtPlugin := true

name := "sbt-license-plugin"

organization := "com.banno"

version := "0.1.5-SNAPSHOT"

scalaVersion := "2.10.5"

scalacOptions ++= Seq("-deprecation", "-unchecked")

scriptedSettings

bintrayPublishSettings

bintrayOrganization in bintray := Some("banno")

repository in bintray := "oss"

licenses ++= Seq(("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html")))
