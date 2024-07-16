package IvanovVasil.OrderManagmentSystem.validation.emailValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private static final String EMAIL_PATTERN =
          "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

  private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

  @Override
  public void initialize(ValidEmail constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);

  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null) {
      return false;
    }
    return pattern.matcher(email).matches();
  }
}