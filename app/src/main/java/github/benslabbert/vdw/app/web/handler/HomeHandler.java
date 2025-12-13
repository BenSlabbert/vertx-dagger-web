/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web.handler;

import github.benslabbert.vdw.codegen.annotation.auth.HasRole;
import github.benslabbert.vdw.codegen.annotation.transaction.AfterCommit;
import github.benslabbert.vdw.codegen.annotation.transaction.BeforeCommit;
import github.benslabbert.vdw.codegen.annotation.transaction.Transactional;
import github.benslabbert.vdw.codegen.annotation.web.WebHandler;
import github.benslabbert.vdw.codegen.annotation.web.WebRequest;
import github.benslabbert.vdw.codegen.annotation.web.WebRequest.Body;
import github.benslabbert.vdw.codegen.annotation.web.WebRequest.Get;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebHandler(path = "/handler")
class HomeHandler {

  private static final Logger log = LoggerFactory.getLogger(HomeHandler.class);

  @Inject
  HomeHandler() {}

  @Transactional
  @HasRole("admin")
  @Get(path = "/raw")
  void ctx(@WebRequest.RoutingContext RoutingContext ctx) {
    ctx.user();
    ctx.end();
  }

  @HasRole("admin")
  @Get(path = "/data")
  ResponseDto ctx(@Valid @Body RequestDto dto) {
    return new ResponseDto(dto.data());
  }

  @HasRole("admin")
  @Get(path = "/data")
  void voidReturn(@Valid @Body RequestDto dto) {
    // do nothing
  }

  @BeforeCommit
  private void beforeCommit() {
    log.info("beforeCommit");
  }

  @AfterCommit
  private void afterCommit() {
    log.info("afterCommit");
  }
}
