/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.app.spi;

import io.vertx.core.Vertx;

public interface VertxSpiDependency {

  default void init(Vertx vertx, Object config) {}
}
