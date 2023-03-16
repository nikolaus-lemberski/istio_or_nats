package com.lemberski.istio.serviceb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zandero.rest.RestRouter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Integer port = config().getInteger("PORT", 8080);
    Router router = RestRouter.register(vertx, Routes.class);

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

}
