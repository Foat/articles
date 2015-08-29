enablePlugins(ScalaJSPlugin)

name := "scala-js-reactive-mouse"
version := "1.0"
scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "com.lihaoyi" %%% "scalarx" % "0.2.8"
)