# Clip Challenge

## Prerequisites
- Spring Boot
- JPA
- H2
- Maven 4
- Java 17

## Description
This applications consist of the following:

- Controller package:  where the basic endpoint is.
- Repository package:  repository package for
- Model package: where entities are stored.
- Request package: objects that represent request.


The project contains a simple API that saves a task in an in-memory database (for the sake of this example lets use a in-memory-database).
The challenge consists of completing as many of the following steps as possible:

## Postman:
Contains a collection with Postman to generate the different use cases, each case has a script to add automatically the Bearer Token.

## Swagger

Swagger is widely used for visualizing APIs, and with Swagger UI it provides online sandbox for frontend developers. For this application we use Springfox implementation of the Swagger 2 specification.

To access please click in the following link:
http://localhost:8080/swagger-ui/index.html

## Security
JSON Web Token, or JWT (“jot”) for short, is a standard for safely passing claims in space constrained environments. It has found its way into all1 major web frameworks. Simplicity,
compactness and usability are key features of its architecture. Although much more complex systems are still in use, JWTs have a broad range of applications.

For more information:
https://jwt.io/

## Database
H2 is an open-source lightweight Java database. It can be embedded in Java applications or run in the client-server mode. Mainly, H2 database can be configured to run as
in-memory database, which means that data will not persist on the disk. Because of embedded database it is not used for production development, but mostly used for development and testing.

For more information:
https://www.h2database.com/html/main.html

## Docker Support
The Maven Wrapper is an easy way to ensure a user of your Maven build has everything necessary to run your Maven build.Install the dependencies.
```sh
mvn -N wrapper:wrapper
```

Now we can run the application without the Docker container (that is, in the host OS):
```sh
./mvnw package && java -jar target/clip-0.0.1-SNAPSHOT.jar 
```

Dockerfile:
Running applications with user privileges helps to mitigate some risks (see, for example, a thread on StackExchange). So, an important improvement to the Dockerfile is to run the application as a non-root user:

```sh
FROM amazoncorretto:17-alpine-jdk
MAINTAINER Miguel Angel Sereno <msereno@kyda.mx>
ENV APP_HOME /app

# Run as non-root
RUN addgroup -S appuser && adduser -S appuser -G appuser
RUN mkdir -m 0755 -p ${APP_HOME}
RUN mkdir -m 0755 -p ${APP_HOME}/bin
RUN mkdir -m 0755 -p ${APP_HOME}/config
RUN mkdir -m 0755 -p ${APP_HOME}/logs
RUN chown -R appuser:appuser ${APP_HOME}
USER appuser

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ${APP_HOME}/bin/app.jar

WORKDIR ${APP_HOME}
ENTRYPOINT ["java","-jar","/app/bin/app.jar"]
```

Execute:
```sh
docker build -t petco/petco-rest-api .
docker run -p 8080:8080 petco/petco-rest-api
```

