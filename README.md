## ASYNC-API

### Requirements (developed and tested on)

*   Java 1.8
*   [Vagrant](http://www.vagrantup.com/ "http://www.vagrantup.com/") (1.6.3) and [VirtualBox](https://www.virtualbox.org/ "https://www.virtualbox.org/") (4.3.30) for callback mock. 

#### steps to prepare and start application:
1. Vagrant must be used to start virtual machine with callback mock server
   
           vagrant plugin install vagrant-vbguest
           vagrant up
        
1. start application from command line
  1. Unix `./gradlew bootRun`
  2. Windows `./gradlew.bat bootRun` (not tested)
       
1. External service is at <http://localhost:8080/service/method> url     

#### Intellij IDEA
* File -> import project
* choose build.gradle

### to unit tests from command line (CI server)
 1. run `./gradlew test`
 1. check unit tests output from console or "build/reports/test/" folder
 
### Generating a WAR file
To package the application as a (exuctable) WAR, type:

`./gradlew bootRepackage`

This will generate two files:

1. "build/libs/asyncapi-0.0.1.war" - This one is an executable WAR file. Instead of deploying on an application server, many people find it easier to just have an exectuable WAR file (which includes Jetty/Tomcat 8, in fact).
  to run executable war `java -jar asyncapi-0.0.1.war`
2. "asyncapi-0.0.1.war.original" - usual WAR without Jetty/Tomcat libs. Use this to deploy on separate application server

p.s. to override properties in production read <http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files>
p.s.s. if you need to change Spring profile start app as `java -jar asyncapi-0.0.1.war --spring.profiles.active=node2`.