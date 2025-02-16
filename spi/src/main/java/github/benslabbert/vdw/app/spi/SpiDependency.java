/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.app.spi;

public interface SpiDependency extends VertxSpiDependency {

  default String doWork() {
    return "default work value";
  }
}
