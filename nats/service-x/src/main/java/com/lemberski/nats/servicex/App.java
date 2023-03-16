package com.lemberski.nats.servicex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;

public final class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static final String NATS_TOPIC = "serviceb.hello";

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

        // just receiving here, NOT joining the subscriber group
        dispatcher.subscribe(NATS_TOPIC, msg -> {
            LOG.info("Received message from {}", msg.getReplyTo());
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dispatcher.unsubscribe(NATS_TOPIC);
        }));
    }

    private void runForever() throws InterruptedException {
        LOG.info("Service X started...");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            //
        });
    }

}
