/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web;

import github.benslabbert.vdw.codegen.commons.RouterConfigurer;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.SessionHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class RouterFactory {

  private static final Logger log = LoggerFactory.getLogger(RouterFactory.class);
  private final AuthenticationProvider authenticationProvider;
  private final Set<RouterConfigurer> routerConfigurers;
  private final SessionHandler sessionHandler;
  private final Vertx vertx;

  @Inject
  RouterFactory(
      Vertx vertx,
      Set<RouterConfigurer> routerConfigurers,
      SessionHandler sessionHandler,
      AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
    this.routerConfigurers = routerConfigurers;
    this.sessionHandler = sessionHandler;
    this.vertx = vertx;
  }

  public Router createRouter() {
    Router router = Router.router(vertx);
    router
        .route()
        .failureHandler(
            ctx -> {
              if (500 == ctx.statusCode()) {
                // some exception happened during request processing
                return;
              }

              // request was failed with some specific exception
              // ctx.fail(405);
              // for example

              log.error("Router failure handler");
              ctx.response().setStatusCode(500).end();
            })
        .handler(LoggerHandler.create(false, LoggerFormat.DEFAULT))
        .handler(sessionHandler)
        .handler(CorsHandler.create())
        .handler(BodyHandler.create().setBodyLimit(1024L * 100L))
        .handler(BasicAuthHandler.create(authenticationProvider));

    routerConfigurers.forEach(rc -> rc.route(router));
    return router;
  }
}
