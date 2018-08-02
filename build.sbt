name := "scala-benchmarks"

organization := "eugenek"
scalaVersion := "2.11.8"

enablePlugins(JmhPlugin)

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
  "org.scala-lang" % "scala-reflect" % "2.11.8"
)