#  Play 2 Hello World clickstart

This clickstart sets up a SBT build service, repository and a basic Play 2 application.

<a href="https://grandcentral.cloudbees.com/?CB_clickstart=https://raw.github.com/CloudBees-community/play2-clickstart/master/clickstart.json"><img src="https://s3.amazonaws.com/cloudbees-downloads/clickstart/clickstart-now.png"/></a>

Launch this clickstart and glory could be yours too ! Use it as a building block if you like.

You can launch this on Cloudbees via a clickstart automatically, and test this locally if you wish.

## To build this locally:

In the play2-clickstart, open a command line, and by typing either:

    java -Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=384M -jar sbt-launch.jar dist
    play dist # if you have the Play! framework installed

Then deploy it on cloudbees typing:

    bees app:deploy -a MYAPP_ID -t java -RJAVA_OPTS='-Dhttp.port=$app_port' -Rclass=play.core.server.NettyServer -Rclasspath=APP_NAME-VERSION/lib/* -Rargs='$app_dir/app/APP_NAME-VERSION/' dist/APP_NAME-VERSION.zip proxyBuffering=false

## To deploy this locally, use either:

    java -Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=384M -jar sbt-launch.jar run
    play run # if you have the Play! framework installed
