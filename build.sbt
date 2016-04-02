name := "scala-type-madness"

version := "0.0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.0" % Test,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test
)