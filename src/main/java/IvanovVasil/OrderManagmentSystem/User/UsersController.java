package IvanovVasil.OrderManagmentSystem.User;

import IvanovVasil.OrderManagmentSystem.User.authentication.Token;
import IvanovVasil.OrderManagmentSystem.User.authentication.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
}
