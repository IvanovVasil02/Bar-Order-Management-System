package IvanovVasil.OrderManagmentSystem.enumValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

  private Enum<?>[] enums;
  private boolean optional;

  @Override
  public void initialize(ValidEnum constraintAnnotation) {
    enums = constraintAnnotation.enumClass().getEnumConstants();
    optional = constraintAnnotation.optional();

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (optional && value == null || value.isEmpty()) {
      return false;
    }

    for (Enum<?> enumValue : enums) {
      if (enumValue.name().equals(value)) {
        return true;
      }
    }

    return false;
  }
}
