import sbt._

class Project(info: ProjectInfo) extends PluginProject(info) with test.ScalaScripted {
  override def scriptedSbt = "0.7.5"
  override def scriptedBufferLog = false

  override def testAction = testNoScripted
  lazy val default = scripted dependsOn(publishLocal) describedAs("Publishes locally and tests against example projects")
}

