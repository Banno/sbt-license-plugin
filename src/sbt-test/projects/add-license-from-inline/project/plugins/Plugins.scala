import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val licensePlugins = "com.banno" % "sbt-license-plugin" % "latest.integration"
}
