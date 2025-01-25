/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.mva.verticle;

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
    log.info("Starting MainVerticle");

    Future<String> workerDeploymentId =
        vertx
            .deployVerticle(
                WorkerVerticle::new,
                new DeploymentOptions()
                    .setConfig(config())
                    .setThreadingModel(ThreadingModel.WORKER)
                    .setInstances(2))
            .onFailure(t -> log.error("WorkerVerticle deployment failed", t))
            .onSuccess(id -> log.info("WorkerVerticle deployment ID: {}", id));

    Future<String> eventDeploymentId =
        vertx
            .deployVerticle(
                EventVerticle::new,
                new DeploymentOptions()
                    .setConfig(config())
                    .setThreadingModel(ThreadingModel.EVENT_LOOP)
                    .setInstances(2))
            .onFailure(t -> log.error("EventVerticle deployment failed", t))
            .onSuccess(id -> log.info("EventVerticle deployment ID: {}", id));

    Future<String> webDeploymentId =
        vertx
            .deployVerticle(
                WebVerticle::new,
                new DeploymentOptions()
                    .setConfig(config())
                    .setThreadingModel(ThreadingModel.EVENT_LOOP)
                    .setInstances(2))
            .onFailure(t -> log.error("WebVerticle deployment failed", t))
            .onSuccess(id -> log.info("WebVerticle deployment ID: {}", id));

    Future<String> virtualDeploymentId =
        vertx
            .deployVerticle(
                VirtualThreadVerticle::new,
                new DeploymentOptions()
                    .setConfig(config())
                    .setThreadingModel(ThreadingModel.VIRTUAL_THREAD)
                    .setInstances(2))
            .onFailure(t -> log.error("VirtualThreadVerticle deployment failed", t))
            .onSuccess(id -> log.info("VirtualThreadVerticle deployment ID: {}", id));

    Future.all(workerDeploymentId, eventDeploymentId, webDeploymentId, virtualDeploymentId)
        .onComplete(
            ar -> {
              if (ar.failed()) startPromise.fail(ar.cause());
              else startPromise.complete();
            });
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    log.info("Stopping MainVerticle");
    stopPromise.complete();
  }
}
