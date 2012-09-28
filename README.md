#  Play 2 Hello World clickstart

This clickstart sets up a SBT build service, repository and a basic Play 2 application.

<a href="https://grandcentral.cloudbees.com/?CB_clickstart=https://raw.github.com/CloudBees-community/play2-clickstart/master/clickstart.json"><img src="https://s3.amazonaws.com/cloudbees-downloads/clickstart/clickstart-now.png"/></a>

Launch this clickstart and glory could be yours too ! Use it as a building block if you like.

You can launch this on Cloudbees via a clickstart automatically, or follow the instructions below. 

# Deploying manually: 

## To build and deploy this on CloudBees, follow those steps:

Create application:

    bees app:create MYAPP_ID

Create a new software project in Jenkins, changing the following:

* Add this git repository (or yours, with this code) on Jenkins
* Change JDK to:
    
        Sun JDK 1.6 (Latest)
    
* Add an "Execute Shell" build step with:
    
        java -Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=384M -jar sbt-launch.jar -Dsbt.log.noformat=true dist
    
* Also add a post-build step "Deploy to CloudBees" with those parameters:

        Applications: First Match
        Application Id: MYAPP_ID
        Filename Pattern: dist/APP_NAME-VERSION.zip
    
Then finally update your application from your own computer:
    
    bees config:set -a MYAPP_ID runtime.JAVA_OPTS='-Dhttp.port=$app_port' runtime.class=play.core.server.NettyServer runtime.classpath=APP_NAME-VERSION/lib/* -runtime.args='$app_dir/app/APP_NAME-VERSION/'
    bees app:restart MYAPP_ID
    bees app:update MYAPP_ID containerType=java proxyBuffering=false

## To build this locally:

In the play2-clickstart, open a command line, and by typing either:

    java -Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=384M -jar sbt-launch.jar dist
    play dist # if you have the Play! framework installed

Then deploy it on cloudbees typing:

    bees app:deploy -a MYAPP_ID -t java -RJAVA_OPTS='-Dhttp.port=$app_port' -Rclass=play.core.server.NettyServer -Rclasspath=APP_NAME-VERSION/lib/* -Rargs='$app_dir/app/APP_NAME-VERSION/' dist/APP_NAME-VERSION.zip proxyBuffering=false

## To deploy this locally, use either:

    java -Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=384M -jar sbt-launch.jar run
    play run # if you have the Play! framework installed
