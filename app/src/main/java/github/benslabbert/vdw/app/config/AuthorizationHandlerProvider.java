/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.config;

import github.benslabbert.vdw.codegen.commons.RoleAuthorizationHandlerProvider;
import io.vertx.core.Future;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.authorization.RoleBasedAuthorization;
import io.vertx.ext.web.handler.AuthorizationHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
class AuthorizationHandlerProvider
    implements RoleAuthorizationHandlerProvider, AuthorizationProvider {

  @Inject
  AuthorizationHandlerProvider() {}

  @Override
  public AuthorizationHandler forRole(String role) {
    return AuthorizationHandler.create(RoleBasedAuthorization.create(role))
        .addAuthorizationProvider(this);
  }

  @Override
  public String getId() {
    return "example";
  }

  @Override
  public Future<Void> getAuthorizations(User user) {
    if (user.authorizations().isEmpty()) {
      user.authorizations().put(getId(), RoleBasedAuthorization.create("admin"));
    }
    return Future.succeededFuture();
  }
}