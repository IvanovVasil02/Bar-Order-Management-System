package IvanovVasil.OrderManagmentSystem.User.authentication;

import IvanovVasil.OrderManagmentSystem.validation.emailValidator.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRegistrationDTO(@NotBlank(message = "The name cannot be blank")
                                  @Size(min = 5, max = 30, message = "Name not valid")
                                  String name,
                                  @NotBlank(message = "The surname cannot be null")
                                  @Size(min = 5, max = 30, message = "Surname not valid")
                                  String surname,
                                  @ValidEmail(message = "Email not valid")
                                  String email,
                                  @NotBlank(message = "Password cannot be null")
                                  @Size(min = 8, max = 30, message = "Password too short")
                                  String password,
                                  @NotBlank(message = "Phone number cannot be null")
                                  String phone,
                                  @NotBlank(message = "Address cannot be null")
                                  @Size(min = 8, max = 30, message = "Address not valid")
                                  String address) {
}
