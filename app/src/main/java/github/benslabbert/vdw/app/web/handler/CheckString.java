/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdw.app.web.handler;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckStringValidator.class)
@Documented
public @interface CheckString {

  String message() default "{message_1}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  boolean strict() default false;
}
