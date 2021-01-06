name := "scala-benchmarks"

organization := "eugenek"
scalaVersion := "2.12.12"

enablePlugins(JmhPlugin)

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.spire-math" %% "debox" % "0.8.0"
)