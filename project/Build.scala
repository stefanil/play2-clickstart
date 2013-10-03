import sbt._
import Keys._
import play.Project._
import cloudbees.Plugin._

object ApplicationBuild extends Build {

  val appName         = "play2-java-clickstart"
  val appVersion      = "0.1"
  val appDependencies = Seq(
    javaCore,
    javaJdbc,
    javaEbean,
    "mysql" % "mysql-connector-java" % "5.1.21", jdbc, anorm
  )

  val main = play.Project(appName, appVersion, appDependencies)
    .settings(testOptions in Test += Tests.Argument("junitxml", "console"))
    .settings(cloudBeesSettings :_*)
    .settings(CloudBees.applicationId := Some("devel/play2-java-test"))

}