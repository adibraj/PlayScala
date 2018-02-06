name := """First Demo App"""
organization := "com.synerzip"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += ws
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.3"
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "x.x.x" % "test"
)
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1"
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.synerzip.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.synerzip.binders._"
