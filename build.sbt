name := "scala-http-clients"

version := "0.0.1"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
  "org.scalatest" %% "scalatest" % "2.1.5" % "test",
  "com.github.tomakehurst" % "wiremock" % "1.46" % "test"
)