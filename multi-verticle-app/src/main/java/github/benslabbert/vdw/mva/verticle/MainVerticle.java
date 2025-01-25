/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.mva.verticle;

import static io.vertx.core.Future.all;
import static io.vertx.core.ThreadingModel.EVENT_LOOP;
import static io.vertx.core.ThreadingModel.VIRTUAL_THREAD;
import static io.vertx.core.ThreadingModel.WORKER;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.ThreadingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) {
    log.info("Starting MainVerticle config: {}", config());

    Future<String> workerDeployment =
        vertx.deployVerticle(WorkerVerticle::new, getDeploymentOptions(WORKER));
    Future<String> eventDeployment =
        vertx.deployVerticle(EventVerticle::new, getDeploymentOptions(EVENT_LOOP));
    Future<String> webDeployment =
        vertx.deployVerticle(WebVerticle::new, getDeploymentOptions(EVENT_LOOP));
    Future<String> virtualDeployment =
        vertx.deployVerticle(VirtualThreadVerticle::new, getDeploymentOptions(VIRTUAL_THREAD));

    all(workerDeployment, eventDeployment, webDeployment, virtualDeployment)
        .onComplete(
            ar -> {
              if (ar.failed()) startPromise.fail(ar.cause());
              else startPromise.complete();
            });
  }

  private DeploymentOptions getDeploymentOptions(ThreadingModel threadingModel) {
    return new DeploymentOptions()
        .setConfig(config())
        .setThreadingModel(threadingModel)
        .setInstances(2);
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    log.info("Stopping MainVerticle");
    stopPromise.complete();
  }
}
