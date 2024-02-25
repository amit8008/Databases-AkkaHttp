ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

val AkkaVersion = "2.9.0"
val AkkaHttpVersion = "10.6.0"

lazy val root = (project in file("."))
  .settings(
    name := "databases_with_akka_http",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion

    )
  )
