import sbt._

class Project(info: ProjectInfo) extends PluginProject(info) with test.ScalaScripted {
  override def scriptedSbt = "0.7.5"
  override def scriptedBufferLog = false

  override def testAction = scripted
}

