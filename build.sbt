name := "nest-api"

//common settings for the project and subprojects
lazy val commonSettings = Seq(
	organization := "com.alex",
	version := "0.1",
	scalaVersion := "2.11.8",
	scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-target:jvm-1.8")
)

lazy val root = (project in file("."))
	.settings(commonSettings: _*)
	.settings(routesGenerator := InjectedRoutesGenerator)
	.settings(
		libraryDependencies += "com.typesafe.slick" %% "slick" % "3.1.1",
		libraryDependencies += "com.github.tminglei" %% "slick-pg" % "0.14.3",
		libraryDependencies += "com.github.tminglei" %% "slick-pg_date2" % "0.14.3",
		libraryDependencies += "com.typesafe.play" %% "play-slick" % "2.0.2",
		libraryDependencies += play.sbt.Play.autoImport.cache
	)
  .enablePlugins(PlayScala)
