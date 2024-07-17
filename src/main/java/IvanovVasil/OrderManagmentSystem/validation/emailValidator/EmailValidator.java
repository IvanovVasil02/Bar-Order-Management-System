package IvanovVasil.OrderManagmentSystem.validation.emailValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private static final String EMAIL_PATTERN =
          "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

  private Pattern pattern = Pattern.compile(EMAIL_PATTERN);


  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null) {
      return false;
    }
    return pattern.matcher(email).matches();
  }
}