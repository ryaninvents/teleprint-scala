name := "teleprint"

version := "0.0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

libraryDependencies += "org.scream3r" % "jssc" % "2.8.0"

play.Project.playScalaSettings
