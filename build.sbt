name := "svstm"

version := "0.0"

scalaVersion := "2.10.2"

//libraryDependencies += ("org.scala-stm" %% "scala-stm" % "0.7")

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

testOptions in Test += Tests.Setup( () => System.setProperty("scala.stm.impl", "scala.concurrent.stm.svstm.SVSTM") )