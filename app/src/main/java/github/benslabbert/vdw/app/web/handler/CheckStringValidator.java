/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.app.web.handler;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckStringValidator implements ConstraintValidator<CheckString, String> {

  private static final Logger log = LoggerFactory.getLogger(CheckStringValidator.class);

  private boolean strict;

  public CheckStringValidator() {
    log.debug("Creating new instance of CheckStringValidator");
  }

  @Override
  public void initialize(CheckString checkString) {
    log.debug("CheckStringValidator initialize");
    this.strict = checkString.strict();
    log.debug("CheckStringValidator strict: {}", strict);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    log.debug("CheckStringValidator isValid: {}", value);

    if (strict) {
      log.debug("CheckStringValidator strict validation");
      return false;
    }

    log.debug("CheckStringValidator relaxed validation");
    return true;
  }
}
