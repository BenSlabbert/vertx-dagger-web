/* Licensed under Apache-2.0 2024. */
package github.benslabbert.vdw.app.config;

import github.benslabbert.vdw.codegen.commons.ValidatorProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.Duration;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

@Singleton
final class ValidatorFactoryProvider implements ValidatorProvider, AutoCloseable {

  private final ValidatorFactory factory;
  private final Validator validator;

  @Inject
  ValidatorFactoryProvider() {
    // https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#chapter-bootstrapping
    this.factory =
        Validation.byProvider(HibernateValidator.class)
            .configure()
            // do not use XML
            .ignoreXmlConfiguration()
            // fluent programmatic configuration
            .temporalValidationTolerance(Duration.ofMillis(100L))
            .showValidatedValuesInTraceLogs(true)
            .failFast(true)
            .messageInterpolator(
                new ResourceBundleMessageInterpolator(
                    new PlatformResourceBundleLocator("messages")) {})
            .buildValidatorFactory();
    this.validator = this.factory.getValidator();
  }

  @Override
  public void close() {
    if (factory != null) {
      factory.close();
    }
  }

  @Override
  public Validator getValidator() {
    return validator;
  }
}
