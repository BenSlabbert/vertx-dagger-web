/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.vertx.core.ThreadingModel;
import io.vertx.launcher.application.HookContext;
import io.vertx.launcher.application.VertxApplication;
import io.vertx.launcher.application.VertxApplicationHooks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends VertxApplication {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);

    log.info("Starting");
    int code = new Main(args).launch();
    log.info("launch successful ? {}", 0 == code);
  }

  private Main(String[] args) {
    super(
        args,
        new VertxApplicationHooks() {
          @Override
          public void beforeDeployingVerticle(HookContext context) {
            log.info("beforeDeployingVerticle");
            // update deploymentOptions
            context
                .deploymentOptions()
                .setThreadingModel(ThreadingModel.VIRTUAL_THREAD)
                .setWorkerPoolSize(1)
                .setWorkerPoolName("worker-pool");
          }

          @Override
          public void beforeStartingVertx(HookContext context) {
            log.info("beforeStartingVertx");
            // update vertxOptions
            context
                .vertxOptions()
                .setWorkerPoolSize(1)
                .setEventLoopPoolSize(1)
                .setInternalBlockingPoolSize(1);
          }
        });
  }
}
