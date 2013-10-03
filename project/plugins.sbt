// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Sonatype Repo containing sbt-cloudbees-play-plugin
resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.1.4")

// Use sbt-cloudbees-play-plugin for deploying the application with play cloudbees-deploy
// see https://github.com/CloudBees-community/sbt-cloudbees-play-plugin/
addSbtPlugin("com.cloudbees.deploy.play" % "sbt-cloudbees-play-plugin" % "0.5-SNAPSHOT")
