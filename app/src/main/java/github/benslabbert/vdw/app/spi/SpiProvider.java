/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.app.spi;

import dagger.Module;
import dagger.Provides;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.ServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Module
public class SpiProvider {

  private static final Logger log = LoggerFactory.getLogger(SpiProvider.class);

  private SpiProvider() {}

  @Provides
  @Singleton
  static SpiDependency provideSpiDependency() {
    ServiceLoader<SpiDependency> load =
        ServiceLoader.load(SpiDependency.class, SpiProvider.class.getClassLoader());

    long count = load.stream().count();
    log.info("Found {} spi dependencies", count);
    Optional<SpiDependency> first = load.findFirst();

    if (first.isPresent()) {
      return first.get();
    }

    log.warn("No SpiDependency found, use the default one");
    return new SpiDependency() {};
  }
}
