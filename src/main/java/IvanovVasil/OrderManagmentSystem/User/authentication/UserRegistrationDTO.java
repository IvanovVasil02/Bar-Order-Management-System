package IvanovVasil.OrderManagmentSystem.User.authentication;

import IvanovVasil.OrderManagmentSystem.validation.emailValidator.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRegistrationDTO(@NotBlank(message = "The name cannot be null")
                                  String name,
                                  @NotBlank(message = "The surname cannot be null")
                                  String surname,
                                  @ValidEmail(message = "Email not valid")
                                  String email,
                                  @NotBlank(message = "Password cannot be null")
                                  String password,
                                  @NotBlank(message = "Phone number cannot be null")
                                  String phone,
                                  @NotBlank(message = "Address cannot be null")
                                  String address) {
}
