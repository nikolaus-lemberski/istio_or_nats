package com.lemberski.istio.servicea;

import static java.lang.Integer.valueOf;
import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  private static final String SERVICE_B_HOST = System.getenv().getOrDefault("SERVICE_B_HOST", "service-b");
  private static final String SERVICE_B_PORT = System.getenv().getOrDefault("SERVICE_B_PORT", "8080");

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = createRouter();
    Integer port = config().getInteger("PORT", 8080);

    vertx.createHttpServer()
        .requestHandler(router)
        .listen(port, http -> {
          if (http.succeeded()) {
            startPromise.complete();
            LOG.info("HTTP server started on port {}", port);
          } else {
            startPromise.fail(http.cause());
          }
        });
  }

  private Router createRouter() {
    Router router = Router.router(vertx);

    router.route("/").handler(ctx -> {
      WebClient webClient = WebClient.create(vertx);
      webClient
          .get(valueOf(SERVICE_B_PORT), SERVICE_B_HOST, "/hello")
          .send()
          .onSuccess(res -> {
            String message = format("Service A <- %s", res.bodyAsString());
            out(message, ctx);
          })
          .onFailure(e -> {
            LOG.error("Error calling Service B", e);
            String message = format("Service A <- Service B error (%s)", e.getMessage());
            out(message, ctx);
          });
    });

    router.route("/health").handler(ctx -> {
      out("UP", ctx);
    });
    return router;
  }

  private void out(String message, RoutingContext ctx) {
    HttpServerResponse response = ctx.response();
    response.putHeader("content-type", "text/plain");
    response.end(message + "\n");
  }

}
