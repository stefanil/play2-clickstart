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
* Add an "Execute Shell" build step with:
    
        java -Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=384M -jar sbt-launch.jar -Dsbt.log.noformat=true dist
    
* Also check "Deploy to CloudBees" with those parameters:

        Applications: First Match
        Application Id: MYAPP_ID
        Filename Pattern: dist/APP_NAME-VERSION.zip
        Advanced... > Environment: java
    
Then finally update your application from your own computer:
    
    bees app:update MYAPP_ID runtime.JAVA_OPTS='-Dhttp.port=$app_port' runtime.class=play.core.server.NettyServer runtime.classpath=APP_NAME-VERSION/lib/* runtime.args='$app_dir/app/APP_NAME-VERSION/' proxyBuffering=false

## To build this locally:

In the play2-clickstart, open a command line, and invoke maven by typing "play dist" to build the distributable .zip file, then deploy it on cloudbees typing:

    bees app:deploy -a MYAPP_ID -t java -RJAVA_OPTS='-Dhttp.port=$app_port' -Rclass=play.core.server.NettyServer -Rclasspath=APP_NAME-VERSION/lib/* -Rargs='$app_dir/app/APP_NAME-VERSION/' dist\APP_NAME-VERSION.zip proxyBuffering=false

## To deploy this locally:

Make sure you have the Play! framework install and inside your type:

    play run
