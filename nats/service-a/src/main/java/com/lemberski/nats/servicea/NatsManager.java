package com.lemberski.nats.servicea;

import java.io.IOException;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;

public class NatsManager {

    private static final Logger LOG = LoggerFactory.getLogger(NatsManager.class);
    //private static final String NATS_SERVICE = System.getenv().getOrDefault("NATS_SERVICE", "nats://nats:4222");
    private static final String NATS_SERVICE = System.getenv().getOrDefault("NATS_SERVICE", "nats://localhost:4222");
    //private static final String NATS_SERVICE = System.getenv().getOrDefault("NATS_SERVICE", "nats://nats-messaging-apps.apps.cluster-v6tdf.v6tdf.sandbox1466.opentlc.com:80");
    private static final int MAX_CONNECTION_RETRIES = 100;
    private Connection connection;
    private int counter;

    public NatsManager() {
        if (connection == null) {
            connection = create();
        }
    }

    Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            //connection = create();
        }

        return connection;
    }

    private Connection create() {
        try {
            Options options = new Options.Builder()
                .server(NATS_SERVICE)
                .reconnectWait(Duration.ofSeconds(1))
                .build();
            connection = Nats.connect(options);
            LOG.info("Successfully connected to NATS.");
            return connection;
        } catch (IOException | InterruptedException e) {
            try {
                if (counter <= MAX_CONNECTION_RETRIES) {
                    LOG.info("Nats not available, waiting... ({})", counter);
                    Thread.sleep(1000);
                    counter++;
                    return create();
                }
            } catch (InterruptedException e1) {
                //
            }
        }

        throw new RuntimeException("Cannot connect to NATS, giving up.");
    }

}
