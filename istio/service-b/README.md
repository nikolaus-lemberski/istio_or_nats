# Service C

Simple service in Java 11 and Javalin Micro Framework.

Endpoints:

* "/"
* "/health"

## Messaging / NATS

The service connects to a NATS server. HTTP calls to "/" make a downstream call via NATS to Service B in request/reply mode of NATS. The response from Service B is added to the Service A response.

## Develop

Open in your IDE and run *App.java*. Or build the project with Maven and run the jar file.

```bash
./mvnw clean package  
java -jar target/service-a-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Container image

With the provided Containerfile you can create a container image.