package IvanovVasil.OrderManagmentSystem.validation.doubleValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DoubleValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDouble {
  String message() default "Invalid double value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
