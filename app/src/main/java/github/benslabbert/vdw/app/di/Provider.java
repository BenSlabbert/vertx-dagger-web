/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.di;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import github.benslabbert.vdw.app.config.ConfigModule;
import github.benslabbert.vdw.app.web.RouterFactory;
import github.benslabbert.vdw.app.web.ServerFactory;
import github.benslabbert.vdw.app.web.handler.HandlerModule;
import io.vertx.core.Vertx;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Component(modules = {Provider.EagerModule.class, HandlerModule.class, ConfigModule.class})
public interface Provider {

  Logger log = LoggerFactory.getLogger(Provider.class);

  @Nullable Void init();

  ServerFactory serverFactory();

  RouterFactory routerFactory();

  Set<AutoCloseable> closeables();

  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder vertx(Vertx vertx);

    Provider build();
  }

  @Module
  final class EagerModule {

    @Inject
    EagerModule() {}

    @Provides
    @Nullable static Void provideEager() {
      // this eagerly builds any parameters specified and returns nothing
      log.info("eager init");
      return null;
    }
  }
}
