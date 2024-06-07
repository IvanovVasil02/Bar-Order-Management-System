package IvanovVasil.OrderManagmentSystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataRunner implements ApplicationRunner {
  @Autowired
  UsersRepository ur;
  @Autowired
  PasswordEncoder bcrypt;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (ur.findByEmail("admin@gmail.com") == null) {
      User admin = new User();
      admin.setEmail("admin@gmail.com");
      admin.setPassword(bcrypt.encode("1234567888"));
      ur.save(admin);
    }
  }

}
