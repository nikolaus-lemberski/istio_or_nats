# Service C

Simple service in Java 11 and Javalin Micro Framework.

Endpoints:

* "/"
* "/health"

## Messaging / NATS

The service connects to a NATS server, listens in a queue group to a topic and responds with the service name and hostname.

## Develop

Open in your IDE and run *App.java*. Or build the project with Maven and run the jar file.

```bash
./mvnw clean package  
java -jar target/service-b-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Container image

With the provided Containerfile you can create a container image.