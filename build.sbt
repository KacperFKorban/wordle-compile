val scala3Version = "3.1.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "wordle-compile",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= List(
      "org.scala-lang" %% "scala3-compiler" % scala3Version
    )
  )
