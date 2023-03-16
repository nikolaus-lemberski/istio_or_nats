# Service C

Message listener service in Java 11.

## Messaging / NATS

The service connects to a NATS server, listens to a topic and logs the messages.

## Develop

Open in your IDE and run *App.java*. Or build the project with Maven and run the jar file.

```bash
./mvnw clean package  
java -jar target/service-x-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Container image

With the provided Containerfile you can create a container image.