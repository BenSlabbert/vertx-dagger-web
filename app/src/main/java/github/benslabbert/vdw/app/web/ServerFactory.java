/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web;

import github.benslabbert.vdw.codegen.config.ApplicationConfig;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.ext.web.Router;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Objects;

@Singleton
public class ServerFactory {

  private final ApplicationConfig.HttpConfig httpConfig;
  private final Vertx vertx;

  @Inject
  ServerFactory(Vertx vertx, ApplicationConfig applicationConfig) {
    this.httpConfig = Objects.requireNonNull(applicationConfig.httpConfig());
    this.vertx = vertx;
  }

  public HttpServer create(Router router) {
    return vertx
        .createHttpServer(
            new HttpServerOptions()
                .setTracingPolicy(TracingPolicy.ALWAYS)
                .setPort(httpConfig.port())
                .setHost("0.0.0.0"))
        .requestHandler(router);
  }
}
