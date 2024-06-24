package IvanovVasil.OrderManagmentSystem.validation.uuidsValidator;

import IvanovVasil.OrderManagmentSystem.validation.enumsValidator.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidUUID {
  String message() default "Invalid UUID";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
