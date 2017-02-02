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
		libraryDependencies ++= Seq(
			"com.typesafe.slick" %% "slick" % "3.1.1",
			"com.typesafe.slick" %% "slick-codegen" % "3.1.1",
			"com.github.tminglei" %% "slick-pg" % "0.14.3",
			"com.github.tminglei" %% "slick-pg_date2" % "0.14.3",
			"com.typesafe.play" %% "play-slick" % "2.0.2",
			"com.typesafe.play" %% "play-slick-evolutions" % "2.0.2",
			play.sbt.Play.autoImport.cache,
			play.sbt.Play.autoImport.ws
		)
	)
  .enablePlugins(PlayScala)

addCommandAlias("tables", "run-main utils.db.SourceCodeGenerator")
