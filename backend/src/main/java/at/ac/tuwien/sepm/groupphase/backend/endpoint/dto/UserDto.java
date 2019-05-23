package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class UserDto {

  private String username;

  private String password;

  private Integer failedLoginCounter;

  private Boolean enabled;

  private Boolean isAdmin;

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

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean admin) {
    isAdmin = admin;
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
        && isAdmin == userDto.isAdmin
        && username.equals(userDto.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, failedLoginCounter, enabled, isAdmin);
  }
}
