package com.lemberski.nats.servicex;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nats.client.Connection;
import io.nats.client.Nats;

public class NatsManager {

    private static final Logger LOG = LoggerFactory.getLogger(NatsManager.class);
    private static final String NATS_SERVICE = System.getenv().getOrDefault("NATS_SERVICE", "nats://nats:4222");
    private static final int MAX_CONNECTION_RETRIES = 60;
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
            connection = create();
        }

        return connection;
    }

    private Connection create() {
        try {
            connection = Nats.connect(NATS_SERVICE);
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
