/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app;

import github.benslabbert.vertxdaggercommons.launcher.CustomApplicationHooks;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.vertx.launcher.application.VertxApplication;
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
    super(args, new CustomApplicationHooks());
  }
}
