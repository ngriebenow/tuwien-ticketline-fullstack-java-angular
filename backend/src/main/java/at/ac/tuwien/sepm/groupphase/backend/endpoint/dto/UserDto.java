package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class UserDto {

  private String username;

  private String password;

  private int failedLoginCounter;

  private boolean enabled;

  private boolean isAdmin;

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

  public int getFailedLoginCounter() {
    return failedLoginCounter;
  }

  public void setFailedLoginCounter(int failedLoginCounter) {
    this.failedLoginCounter = failedLoginCounter;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }
}
