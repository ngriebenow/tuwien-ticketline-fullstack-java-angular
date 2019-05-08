package at.ac.tuwien.sepm.groupphase.backend.entity;

public class User {

  private int username;

  private byte[] password;

  private int failedLoginCounter;

  private boolean isLocked;

  public int getUsername() {
    return username;
  }

  public void setUsername(int username) {
    this.username = username;
  }

  public byte[] getPassword() {
    return password;
  }

  public void setPassword(byte[] password) {
    this.password = password;
  }

  public int getFailedLoginCounter() {
    return failedLoginCounter;
  }

  public void setFailedLoginCounter(int failedLoginCounter) {
    this.failedLoginCounter = failedLoginCounter;
  }

  public boolean isLocked() {
    return isLocked;
  }

  public void setLocked(boolean locked) {
    isLocked = locked;
  }
}
