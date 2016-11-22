lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "hwscala",
    libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0",
    libraryDependencies +=  "org.json4s" %% "json4s-jackson" % "3.5.0"
  )