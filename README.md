scalatra-mongo-sample
=====================

Simple Scalatra web app that stores and retrieves messages from Mongo.  Demonstrates use of the [cloudfoundry-runtime](https://github.com/cloudfoundry/vcap-java/tree/master/cloudfoundry-runtime) library to connect to services.  Code was obtained from this blog post: http://janxspirit.blogspot.co.uk/2011/01/quick-webb-app-with-scala-mongodb.html

### Deploying to Cloud Foundry

To deploy the application to Cloud Foundry, simply build the WAR and push it to Cloud Foundry using the provided manifest.yml file, supplying the application name and URL.

```bash
mvn clean package
vmc push
Would you like to deploy from the current directory? [Yn]:
Application Name: messageapp
Application Deployed URL [messageapp.cloudfoundry.com]:
Pushing application 'messageapp'...
```