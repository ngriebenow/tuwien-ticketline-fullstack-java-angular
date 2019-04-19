# ss19_sepm_inso_11

This repository contains an Angular Frontend and a Spring-Boot REST-Backend for the fictive Austrian company Ticketline, which primarily deals with, as the name already suggests, ticket selling for various events.

The project offers sophisticated tools like, among others, a variety of search possibilities for events, a graphical hall plan for ticket selling as well as ticket reservation and the printing of invoices adhering to the Austrian law.

## Building and Testing
Since the project consists of two separate applications it can be a hassle to configure build and run each of them when presenting the whole system or performing manual tests on it. To alleviate this maven has been set up to take care of it.  
Assuming you are in the projects root directory you can build the system as a jar file by executing the following command.  

``` shell
mvn clean install
```

This will run tests and then only if all of them pass build first the front- and then the back end. In case you need to disable the tests you can do so by passing the `-Dskip.npm` and `-DskipTests` for front- and back end respectively. The newly created jar file can be found under `backend/target/backend-4.0.0-SNAPSHOT.jar` and then ran with  

``` shell
java -jar backend/target/backend-4.0.0-SNAPSHOT.jar
```

The tomcat server will be running on `localhost:8080` and serve the front end on the root url, so you can reach it by navigating to http://localhost:8080/ with the browser of you choice.  

In a similar way you can run tests for both applications with a single command.  

``` shell
mvn clean test
```
