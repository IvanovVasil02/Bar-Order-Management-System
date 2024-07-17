package IvanovVasil.OrderManagmentSystem.User;

import IvanovVasil.OrderManagmentSystem.User.authentication.UserLoginDTO;
import IvanovVasil.OrderManagmentSystem.User.authentication.UserRegistrationDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
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

  public User userRegistration(UserRegistrationDTO body) {
    ur.findByEmail(body.email()).ifPresent(patient -> {
      throw new BadRequestException("The email " + patient.getEmail() + " is alredy used.");
    });

    User user = User
            .builder()
            .name(body.name())
            .surname(body.surname())
            .email(body.email())
            .password(body.password())
            .phone(body.phone())
            .address(body.address())
            .build();

    return save(user);
  }

  public String authenticateUser(UserLoginDTO body) {
    User user = ur.findByEmail(body.email()).orElseThrow(() -> new NotFoundException("There is no account with this email"));

    if (bcrypt.matches(body.password(), user.getPassword())) {
      return jwtTools.createToken(user);
    } else {
      throw new UnauthorizedException("Email or password invalid.");
    }
  }
}
