/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.verticle;

import github.benslabbert.vdw.app.di.DaggerProvider;
import github.benslabbert.vdw.app.di.Provider;
import github.benslabbert.vdw.app.spi.SpiDependency;
import github.benslabbert.vdw.app.web.RouterFactory;
import github.benslabbert.vdw.app.web.ServerFactory;
import github.benslabbert.vdw.codegen.config.ApplicationConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(DefaultVerticle.class);

  private HttpServer httpServer = null;
  private Provider provider = null;

  private void setHttpServer(HttpServer httpServer) {
    this.httpServer = httpServer;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    log.info("Starting verticle");
    ApplicationConfig applicationConfig = ApplicationConfig.fromJson(config());
    provider = DaggerProvider.builder().vertx(vertx).applicationConfig(applicationConfig).build();
    provider.init();
    SpiDependency spiDependency = provider.spiDependency();
    spiDependency.init(vertx, new Object());
    String s = spiDependency.doWork();
    log.info("SpiDependency returned: {}", s);

    ServerFactory serverFactory = provider.serverFactory();
    RouterFactory routerFactory = provider.routerFactory();
    Router router = routerFactory.createRouter();

    serverFactory
        .create(router)
        .listen()
        .onComplete(
            res -> {
              if (res.succeeded()) {
                log.info("listening for requests on port: {}", res.result().actualPort());
                setHttpServer(res.result());
                startPromise.complete();
              } else {
                startPromise.fail(res.cause());
              }
            });
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    log.info("Stopping verticle");

    if (null != provider) {
      provider
          .closeables()
          .forEach(
              c -> {
                try {
                  c.close();
                } catch (Exception e) {
                  log.error("closing resource failed", e);
                }
              });
    }

    if (null != httpServer) {
      log.info("Stopping http server");
      httpServer
          .close()
          .onComplete(
              e -> {
                if (e.succeeded()) {
                  log.info("http server closed");
                  stopPromise.complete();
                } else {
                  log.error("stopping http server failed", e.cause());
                  stopPromise.fail(e.cause());
                }
              });
      return;
    }

    stopPromise.complete();
  }
}
