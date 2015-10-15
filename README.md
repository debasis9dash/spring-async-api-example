## ASYNC-API

### Requirements (developed and tested on)

*   Java 1.8
*   [Vagrant](http://www.vagrantup.com/ "http://www.vagrantup.com/") (1.6.3) and [VirtualBox](https://www.virtualbox.org/ "https://www.virtualbox.org/") (4.3.30) for callback mock. 

#### steps to prepare and start application:
1. Vagrant must be used to start virtual machine with callback mock server
   
           vagrant plugin install vagrant-vbguest
           vagrant up
   NB: be sure to have virtualization enabled in BIOS or otherwise Virtualbox will fail     
1. start application from command line
  1. Unix `./gradlew bootRun`
  2. Windows `./gradlew.bat bootRun` (not tested)
       
1. External service is at <http://localhost:8080/service/method> url     

#### Intellij IDEA
* File -> import project
* choose build.gradle

### to unit tests and code coverage from command line (CI server)
 1. run `./gradlew test`
 1. check unit tests output from console or "build/reports/test/" folder
 1. test code coverage can be seen after running `./gradlew test jacocoTestReport` from "build/reports/jacoco/" directory
 
### Generating a WAR file
To package the application as a (exuctable) WAR, type:

`./gradlew bootRepackage`

This will generate two files:

1. "build/libs/asyncapi-0.0.1.war" - This one is an executable WAR file. Instead of deploying on an application server, many people find it easier to just have an exectuable WAR file (which includes Jetty/Tomcat 8, in fact).
  to run executable war `java -jar asyncapi-0.0.1.war`
2. "asyncapi-0.0.1.war.original" - usual WAR without Jetty/Tomcat libs. Use this to deploy on separate application server

p.s. to override properties in production read <http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files>
p.s.s. if you need to change Spring profile start app as `java -jar asyncapi-0.0.1.war --spring.profiles.active=node2`.

# Steps to test cache replication ("horizontal scaling")
1. `vagrant up` start virtualmachine with callback api mock 
1. `./gradlew bootRepackage` build and package application 
1. `./gradlew.bat bootRun` start application first instance (default 8080 port)
1. `java -jar build/libs/asyncapi-0.0.1.war --spring.profiles.active=node2` start second instance of application on 8081 port
1. query second application extenal api <http://localhost:8081/service/method> This should make call to backend server (inside virtualmachine) and it will post data back after random delay (1ms to 6s) to first application service <http://yourhost:8080/callback.service/method>. As callback responses are replicated between application your second instance should show you correct response. 
 