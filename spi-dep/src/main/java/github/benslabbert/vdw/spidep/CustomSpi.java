/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.spidep;

import github.benslabbert.vdw.app.spi.SpiDependency;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSpi implements SpiDependency {

  private static final Logger log = LoggerFactory.getLogger(CustomSpi.class);

  private Vertx vertx;
  private Object config;

  @Override
  public String doWork() {
    log.info("doWork");
    log.info("vertx {}", vertx);
    log.info("config {}", config);
    return "real work";
  }

  @Override
  public void init(Vertx vertx, Object config) {
    this.vertx = vertx;
    this.config = config;
    log.info("init");
  }
}
