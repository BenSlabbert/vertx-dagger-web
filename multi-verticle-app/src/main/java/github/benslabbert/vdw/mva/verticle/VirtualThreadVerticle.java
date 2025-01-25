/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.mva.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class VirtualThreadVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(VirtualThreadVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) {
    log.info("Starting VirtualThreadVerticle");
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    log.info("Stopping VirtualThreadVerticle");
    stopPromise.complete();
  }
}
