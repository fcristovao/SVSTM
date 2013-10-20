import sbt._

case class Remote(uri: String, branch: String)

object Config {
  val scalaSTM = Remote("https://github.com/fcristovao/scala-stm.git", "svstm")
}

object Projects {
  lazy val scalaSTM = RootProject(uri("%s#%s".format(Config.scalaSTM.uri, Config.scalaSTM.branch)))
}

object SVSTMBuild extends Build {
  lazy val svstm = Project("svstm", file(".")) dependsOn(Projects.scalaSTM)
}