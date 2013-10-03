import sbt._
import Keys._
import play.Project._
import cloudbees.Plugin._

object ApplicationBuild extends Build {

  val accountName     = "devel"
  val appName         = "play2-java-clickstart"  
  val appVersion      = "0.1"
  
  val appDependencies = Seq(
    javaCore,
    javaJdbc,
    javaEbean,
    "mysql" % "mysql-connector-java" % "5.1.21"
  )

  val main = play.Project(appName, appVersion, appDependencies)
    .settings(      
      testOptions in Test ~= { args =>
        for {
          arg <- args
          val ta: Tests.Argument = arg.asInstanceOf[Tests.Argument]
          val newArg = if(ta.framework == Some(TestFrameworks.JUnit)) ta.copy(args = List.empty[String]) else ta
        } yield newArg
      }
    )
    .settings(cloudBeesSettings :_*)
    .settings(CloudBees.applicationId := Some(accountName+"/"+appName))

}