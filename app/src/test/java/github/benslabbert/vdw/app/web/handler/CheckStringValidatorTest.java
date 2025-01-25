/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.app.web.handler;

import static org.assertj.core.api.Assertions.assertThat;

import github.benslabbert.vdw.app.di.DaggerProvider;
import github.benslabbert.vdw.app.di.Provider;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
class CheckStringValidatorTest {

  private Provider provider;

  @BeforeEach
  void beforeEach(Vertx vertx) {
    provider = DaggerProvider.builder().vertx(vertx).build();
    provider.init();
  }

  @AfterEach
  void afterEach() {
    provider
        .closeables()
        .forEach(
            closeable -> {
              try {
                closeable.close();
              } catch (Exception e) {
                // do nothing
              }
            });
  }

  @Test
  void t(VertxTestContext tc) {
    Validator validator = provider.validatorProvider().getValidator();
    assertThat(validator.validate(new RequestDto("data")))
        .singleElement()
        .satisfies(
            e ->
                tc.verify(
                    () -> {
                      assertThat(e.getPropertyPath()).hasToString("data");
                      tc.completeNow();
                    }));
  }
}
