/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.web.handler;

import github.benslabbert.vdw.codegen.annotation.HasRole;
import github.benslabbert.vdw.codegen.annotation.WebHandler;
import github.benslabbert.vdw.codegen.annotation.WebRequest;
import github.benslabbert.vdw.codegen.annotation.WebRequest.Body;
import github.benslabbert.vdw.codegen.annotation.WebRequest.Get;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@WebHandler(path = "/handler")
class HomeHandler {

  @Inject
  HomeHandler() {}

  @HasRole("admin")
  @Get(path = "/raw")
  void ctx(@WebRequest.RoutingContext RoutingContext ctx) {
    ctx.end();
  }

  @HasRole("admin")
  @Get(path = "/data")
  ResponseDto ctx(@Valid @Body RequestDto dto) {
    return new ResponseDto(dto.data());
  }
}
