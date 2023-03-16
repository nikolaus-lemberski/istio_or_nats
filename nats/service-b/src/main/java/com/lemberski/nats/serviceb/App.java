package com.lemberski.nats.serviceb;

import static java.lang.String.format;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;

public final class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static final String HOSTNAME = System.getenv().getOrDefault("HOSTNAME", "localhost");
    private static final String NATS_TOPIC = "serviceb.hello";
    private static final String NATS_GROUP = "serviceb.queue.group";

    private Integer counter = 0;

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.startMessagingClient();
        app.runForever();
    }

    private void startMessagingClient() {
        NatsManager natsManager = new NatsManager();
        Connection nats = natsManager.getConnection();

        Dispatcher dispatcher = nats.createDispatcher(msg -> {
            //
        });
        dispatcher.subscribe(NATS_TOPIC, NATS_GROUP, msg -> {
            LOG.info("Received message from {}", msg.getReplyTo());

            counter++;
            String reply = format("Service B (%s) - %s", hostname(), counter);
            nats.publish(msg.getReplyTo(), reply.getBytes());

            LOG.info("Sent reply to {}}: {}}", msg.getReplyTo(), reply);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dispatcher.unsubscribe(NATS_TOPIC);
        }));
    }

    private String hostname() {
        String dash = "-";
        if (HOSTNAME.contains(dash)) {
            return HOSTNAME.substring(HOSTNAME.lastIndexOf(dash) + 1);
        }

        return HOSTNAME;
    }

    private void runForever() throws InterruptedException {
        LOG.info("Service B started...");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            //
        });
    }

}
