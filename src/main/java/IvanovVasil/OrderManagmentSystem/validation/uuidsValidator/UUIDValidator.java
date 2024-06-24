package IvanovVasil.OrderManagmentSystem.validation.uuidsValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;
import java.util.regex.Pattern;


public class UUIDValidator implements ConstraintValidator<ValidUUID, UUID> {

  private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");

  @Override
  public void initialize(ValidUUID constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(UUID value, ConstraintValidatorContext context) {
    String uuid = String.valueOf(value);
    return value != null && UUID_PATTERN.matcher(uuid).matches();
  }

}
