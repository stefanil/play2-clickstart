#  Integrating a Play Java Application with Cloudbees by using a Clickstart

This Clickstartstart uses Play 2.1.4.

## 1. Initialize the Application on Cloudbees

This clickstart sets up a SBT build service, repository and a basic Play 2 Java application.

<a href="https://grandcentral.cloudbees.com/?CB_clickstart=https://raw.github.com/stefanil/play2-java-clickstart/master/clickstart.json"><img src="https://d3ko533tu1ozfq.cloudfront.net/clickstart/deployInstantly.png"/></a>

Launch this clickstart and glory could be yours too ! Use it as a building block if you like.

You can launch this on Cloudbees via a clickstart automatically, or follow the instructions below. 

If you don't have a cloudbees account, Sign in with GitHub:
<button onClick="javascript:window.location='https://grandcentral.cloudbees.com/authenticate/start?provider=github&login_redirect=/';"><img src="https://grandcentral.cloudbees.com/images/github-icon_40.png" /></button>

## 2. Customize the Jenkins Configuration

Extract infos about the database:

    bees db:info -p DB_ID
    
Adapt the Execute Shell Command (Build Step) by replacing the dummy with concrete values.

    export DATABASE_URL_DB=na 
    export DATABASE_USERNAME_DB=na 
    export DATABASE_PASSWORD_DB=na

## 3. Clone Git Repository

Clone the created Git repository via:   

    git clone %REPO_PATH%
    
See Cloudbees Repositories for the %REPO_PATH%.

## 4. Adapt Build.scala

First set the correct application name (appName) and afterwards the correct account name (accountName), under which the application runs.
Push the changes to the git repository.

## 5. Deploy subsequent changes

There are a few ways you can deploy:
A) Via git push - with the clickstart
B) Via a plugin: play cloudbees-deploy
C) Via the cloudbees SDK: bees app:deploy

A) The given Clickstart configures the Jenkins server, to trigger a build on each push to the Git Repopsitory.

B) To explicitely and manually trigger the deployment you can use Jenkins itself or just use the following command from your local client:   

    play cloudbees-deploy

See https://github.com/CloudBees-community/sbt-cloudbees-play-plugin/ and http://developer.cloudbees.com/bin/view/RUN/Playframework for further infomration on this topic.

C) The third opportunity uses the Cloudbees SDK and as B) does not include git pushes automatically. The distribution get locally assembled.

    play dist
    bees app:deploy -a APP_ID -t play2  dist/APP_NAME-VERSION.zip

## 6. Configure the DB layer for local development

A) You will need a locally running MySQL server for this instance, or 
B) you can use your cloudbees DB created above as part of of the clickstart, or
C) you can use the embedded database H2 by using an alternative application configuration for development.

In both cases you will also need the db password which must be resolved by using the following command:

  bees db:info -p DB_NAME

.. where DB_NAME must be replaced by the name of your database (f.e.: bees db:info -p av-pirna).

### A) Provide the environment variables so Play can connect to your local DB: 
    
    export DATABASE_URL_DB=mysql://URL_TO_DB_HERE/DB_NAME
    export DATABASE_PASSWORD_DB=PASSWORD_HERE
    export DATABASE_USERNAME_DB=USERNAME_HERE

### B) Provide the environment variables so Play can connect to the remote DBaaS: 

First, find the details to to connect from the desktop to your CloudBees DB:

    bees db:info -p DB_ID

For example for using the database with the id av-pirna, extract the information like this:

    $ bees db:info -p av-pirna

    Database name: av-pirna
    Account:       devel
    Status:        active
    Master:        ec2-23-21-211-172.compute-1.amazonaws.com:3306
    Port:          3306
    Username:      av-pirna
    Password:      %Commented out%

Then note the info and set the following environment variables: 

    export DATABASE_URL_DB="mysql://(EC2 host and port from above)/(database username from above)"
    export DATABASE_USERNAME_DB="(from above)"
    export DATABASE_PASSWORD_DB="(from above)"

In case of the previously given example this would end in the following concrete values:

    DATABASE_URL_DB="mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/av-pirna"
    DATABASE_USERNAME_DB="av-pirna"
    DATABASE_PASSWORD_DB="%Commented out%"

Use the following command, and then browse to localhost:9000:

    play -Dconfig.resource="conf/production.conf" run   
    
To get your cloudbees DB info - run bees db:info -p youraccount/appname(from your clickstart) 

### C) Using an alternative Configuration File

play -Dconfig.resource=CONF_FILENAME run


# Manual Remote-Deployment: 

To build and deploy the application on CloudBees manually, follow those steps:

Create application:

    bees app:create MYAPP_ID -t play2
    
Create database:

    bees db:create -u DB_USER -p DB_PASSWORD DBNAME

Bind database as datasource:

    bees app:bind -db DBNAME -a MYAPP_ID -as ExampleDS    


Create a new software project in Jenkins, changing the following:

* Add this git repository (or yours, with this code) on Jenkins
* Change JDK to:
    
        Oracle JDK 1.7 (Latest)
    
* Add an "Execute Shell" build step with:
    
        java -Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=384M -jar /opt/sbt/sbt-launch-0.11.3-2.jar -Dsbt.log.noformat=true dist
    
* Also add a post-build step "Deploy to CloudBees" with those parameters:

        Applications: First Match
        Application Id: MYAPP_ID
        Filename Pattern: dist/*.zip
    
Then finally update your application from your own computer:
    
    bees config:set -a MYAPP_ID -Rjava_version=1.7 containerType=play2 proxyBuffering=false
    bees app:restart MYAPP_ID


# Manual Local-Build and Deployment to Cloudbees:

You will need play2 installed, or sbt (this jenkins build currently uses SBT).

In the play2-clickstart-java directory, open a command line, and then type:

    play dist

Then deploy it on cloudbees typing:

    bees app:deploy -a MYAPP_ID -t play2 -Rjava_version=1.7 dist/*.zip proxyBuffering=false

# Browse your DBaaS on Cloudbees

I suggest to use an extranal client like SQLExplorer or Squirrel SQL. A good tutorial on this can be found here:
 
http://www.comtechies.com/2013/03/how-to-create-and-connect-cloudbees.html

# Proceed with Adapting the Application

The provided application is rather a simple Scala application than a Java application based on Play 2. So the adaptions are quite huge.



