ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

val AkkaVersion = "2.8.5"
val AkkaHttpVersion = "10.5.3"
val slickVersion = "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "databases_with_akka_http",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "ch.qos.logback" % "logback-classic" % "1.4.10",
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "com.typesafe" % "config" % "1.4.2",
      "mysql" % "mysql-connector-java" % "8.0.33"
    )
  )
