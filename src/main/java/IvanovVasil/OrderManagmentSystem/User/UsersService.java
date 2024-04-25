package IvanovVasil.OrderManagmentSystem.User;

import IvanovVasil.OrderManagmentSystem.User.authentication.UserLoginDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import IvanovVasil.OrderManagmentSystem.exceptions.UnauthorizedException;
import IvanovVasil.OrderManagmentSystem.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {

  @Autowired
  UsersRepository ur;

  @Autowired
  PasswordEncoder bcrypt;

  @Autowired
  JWTTools jwtTools;

  public User save(User user) {
    user.setPassword(bcrypt.encode(user.getPassword()));
    return ur.save(user);
  }

  public User findById(UUID userId) {
    return ur.findById(userId).orElseThrow(() -> new NotFoundException(userId));
  }

  public User editUser(UUID id, String newPassword) {
    User user = ur.findById(id).orElseThrow(() -> new NotFoundException(id));
    user.setPassword(bcrypt.encode(newPassword));
    return ur.save(user);
  }

  public String authenticateUser(UserLoginDTO body) {
    System.out.println(body.email() + " " + body.password());
    User user = ur.findByEmail(body.email());
    if (user != null) {
      if (bcrypt.matches(body.password(), user.getPassword())) {
        return jwtTools.createToken(user);
      } else {
        throw new UnauthorizedException("Email or password invalid.");
      }
    } else {
      throw new UnauthorizedException("Email or password invalid.");
    }
  }
}
