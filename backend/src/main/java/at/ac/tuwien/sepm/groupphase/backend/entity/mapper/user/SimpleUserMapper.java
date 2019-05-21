package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.user;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SimpleUserMapper implements UserMapper {
  private BCryptPasswordEncoder encoder;

  public SimpleUserMapper() {
    this.encoder = new BCryptPasswordEncoder();
  }

  @Override
  public User userDtoToUser(UserDto user) {
    User u = new User();
    u.setUsername(user.getUsername());
    u.setPassword(encoder.encode(user.getPassword()));
    if (user.getAdmin()) {
      u.setAuthority("ROLE_ADMIN, ROLE_USER");
    } else {
      u.setAuthority("ROLE_USER");
    }
    u.setEnabled(user.getEnabled());
    u.setFailedLoginCounter(user.getFailedLoginCounter());
    return u;
  }

  @Override
  public UserDto userToUserDto(User user) {
    UserDto u = new UserDto();
    u.setUsername(user.getUsername());
    u.setPassword("");
    if (user.getAuthority().contains("ROLE_ADMIN")) {
      u.setAdmin(true);
    } else {
      u.setAdmin(false);
    }
    u.setFailedLoginCounter(user.getFailedLoginCounter());
    u.setEnabled(user.isEnabled());
    return u;
  }
}
