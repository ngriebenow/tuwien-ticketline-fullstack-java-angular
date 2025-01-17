package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("generateData")
public class UserDataGenerator implements DataGenerator<User> {

  private final Set<Class<?>> dependencies = new HashSet<>();
  private AccountService accountService;

  @Autowired
  public UserDataGenerator(AccountService accountService) {
    this.accountService = accountService;
  }

  @Transactional
  @Override
  public void execute() {
    UserDto admin = new UserDto();
    admin.setUsername("admin");
    admin.setAdmin(true);
    admin.setEnabled(true);
    admin.setFailedLoginCounter(0);
    admin.setPassword("password");
    accountService.saveUser(admin);

    UserDto user = new UserDto();
    user.setUsername("user");
    user.setAdmin(false);
    user.setEnabled(true);
    user.setFailedLoginCounter(0);
    user.setPassword("password");
    accountService.saveUser(user);

    UserDto user2 = new UserDto();
    user2.setUsername("MR2");
    user2.setAdmin(false);
    user2.setEnabled(true);
    user2.setFailedLoginCounter(0);
    user2.setPassword("password");
    accountService.saveUser(user2);
  }

  @Override
  public Class<User> getGeneratedType() {
    return User.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
