name := "scala-http-clients"

version := "0.0.1"

scalaVersion := "2.10.3"

parallelExecution in Test := false

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2.2",
  "io.spray" % "spray-client" % "1.2.1",
  "commons-io" % "commons-io" % "2.4",
  "org.apache.httpcomponents" % "httpclient" % "4.3.1",
  "org.apache.httpcomponents" % "fluent-hc" % "4.3.1",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
  "org.scalatest" %% "scalatest" % "2.1.5" % "test",
  "com.github.tomakehurst" % "wiremock" % "1.46" % "test"
)