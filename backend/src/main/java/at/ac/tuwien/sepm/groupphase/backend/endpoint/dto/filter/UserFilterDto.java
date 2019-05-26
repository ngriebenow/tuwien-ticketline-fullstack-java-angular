package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

import java.util.Objects;

public class UserFilterDto {

  private String username;

  private String role;

  private String locked;

  private Integer page;

  private Integer count;

  @Override
  public String toString() {
    return "UserFilterDto{"
        + "username='"
        + username
        + '\''
        + ", role='"
        + role
        + '\''
        + ", locked="
        + locked
        + ", page="
        + page
        + ", count="
        + count
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserFilterDto that = (UserFilterDto) o;
    return Objects.equals(username, that.username)
        && Objects.equals(role, that.role)
        && Objects.equals(locked, that.locked)
        && Objects.equals(page, that.page)
        && Objects.equals(count, that.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, role, locked, page, count);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getLocked() {
    return locked;
  }

  public void setLocked(String locked) {
    this.locked = locked;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
