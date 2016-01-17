name := "akka-web-crawler"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  val akkaV       = "2.4.0"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "org.jsoup"         % "jsoup"                                 % "1.8+",
    "commons-validator" % "commons-validator"                     % "1.5+"
  )
}