import bintray.Keys._

sbtPlugin := true

name := "sbt-license-plugin"

organization := "com.banno"

version := "0.1.0"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-deprecation", "-unchecked")

scriptedSettings

bintrayPublishSettings

bintrayOrganization in bintray := Some("banno")

repository in bintray := "oss"

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))
