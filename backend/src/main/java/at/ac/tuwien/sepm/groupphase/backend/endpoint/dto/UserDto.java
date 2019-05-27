package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class UserDto {

  private String username;

  private String password;

  private Integer failedLoginCounter;

  private String enabled;

  private String admin;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getFailedLoginCounter() {
    return failedLoginCounter;
  }

  public void setFailedLoginCounter(Integer failedLoginCounter) {
    this.failedLoginCounter = failedLoginCounter;
  }

  public String getEnabled() {
    return enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  public String getAdmin() {
    return admin;
  }

  public void setAdmin(String admin) {
    this.admin = admin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDto userDto = (UserDto) o;
    return failedLoginCounter == userDto.failedLoginCounter
        && enabled == userDto.enabled
        && admin == userDto.admin
        && username.equals(userDto.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, failedLoginCounter, enabled, admin);
  }
}
