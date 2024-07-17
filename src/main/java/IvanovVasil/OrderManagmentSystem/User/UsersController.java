package IvanovVasil.OrderManagmentSystem.User;

import IvanovVasil.OrderManagmentSystem.User.authentication.Token;
import IvanovVasil.OrderManagmentSystem.User.authentication.UserLoginDTO;
import IvanovVasil.OrderManagmentSystem.User.authentication.UserRegistrationDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class UsersController {

  @Autowired
  UsersService us;

  @PostMapping("/login")
  public Token adminLogin(@RequestBody UserLoginDTO body) {
    return new Token(us.authenticateUser(body));
  }

  @PostMapping("/register")
  public User adminLogin(@Valid @RequestBody UserRegistrationDTO body, BindingResult validation) {

    if (validation.hasErrors()) {
      throw new BadRequestException("There was a problem with the registration", validation.getAllErrors());
    }
    return us.userRegistration(body);
  }
}
