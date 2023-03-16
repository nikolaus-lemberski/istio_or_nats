package com.lemberski.nats.servicea;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.time.Duration;

import io.javalin.Javalin;
import io.nats.client.Connection;
import io.nats.client.Message;

public final class App {

    private static final String PORT = System.getenv().getOrDefault("PORT", "8080");
    private static final String NATS_TOPIC = "serviceb.hello";

    public static void main(String[] args) {
        new App().startWebserver();
    }

    private void startWebserver() {
        Javalin app = Javalin.create().start("0.0.0.0", Integer.valueOf(PORT));
        NatsManager natsManager = new NatsManager();

        app.get("/", ctx -> {
            Connection conn = natsManager.getConnection();
            Message msg = conn.request(NATS_TOPIC, null, Duration.ofSeconds(1));
            ctx.result(format("Service A <- %s\n", new String(msg.getData(), UTF_8)));
        });

        app.get("/health", ctx -> ctx.result("UP"));
    }

}
