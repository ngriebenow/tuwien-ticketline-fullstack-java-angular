package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.authentication;

import io.swagger.annotations.ApiModel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ApiModel(
    value = "AuthenticationTokenInfo",
    description = "Informations about the current authentication token")
public class AuthenticationTokenInfo {

  private String username;

  private List<String> roles;

  private LocalDateTime issuedAt;

  private LocalDateTime notBefore;

  private LocalDateTime expireAt;

  private Duration validityDuration;

  private Duration overlapDuration;

  public static AuthenticationTokenInfoBuilder builder() {
    return new AuthenticationTokenInfoBuilder();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public LocalDateTime getIssuedAt() {
    return issuedAt;
  }

  public void setIssuedAt(LocalDateTime issuedAt) {
    this.issuedAt = issuedAt;
  }

  public LocalDateTime getNotBefore() {
    return notBefore;
  }

  public void setNotBefore(LocalDateTime notBefore) {
    this.notBefore = notBefore;
  }

  public LocalDateTime getExpireAt() {
    return expireAt;
  }

  public void setExpireAt(LocalDateTime expireAt) {
    this.expireAt = expireAt;
  }

  public Duration getValidityDuration() {
    return validityDuration;
  }

  public void setValidityDuration(Duration validityDuration) {
    this.validityDuration = validityDuration;
  }

  public Duration getOverlapDuration() {
    return overlapDuration;
  }

  public void setOverlapDuration(Duration overlapDuration) {
    this.overlapDuration = overlapDuration;
  }

  @Override
  public String toString() {
    return "AuthenticationTokenInfo{"
        + "username='"
        + username
        + '\''
        + ", roles="
        + roles
        + ", issuedAt="
        + issuedAt
        + ", notBefore="
        + notBefore
        + ", expireAt="
        + expireAt
        + ", validityDuration="
        + validityDuration
        + ", overlapDuration="
        + overlapDuration
        + '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    AuthenticationTokenInfo that = (AuthenticationTokenInfo) obj;
    return Objects.equals(username, that.username)
        && Objects.equals(roles, that.roles)
        && Objects.equals(issuedAt, that.issuedAt)
        && Objects.equals(notBefore, that.notBefore)
        && Objects.equals(expireAt, that.expireAt)
        && Objects.equals(validityDuration, that.validityDuration)
        && Objects.equals(overlapDuration, that.overlapDuration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        username, roles, issuedAt, notBefore, expireAt, validityDuration, overlapDuration);
  }

  public static final class AuthenticationTokenInfoBuilder {

    private String username;
    private List<String> roles;
    private LocalDateTime issuedAt;
    private LocalDateTime notBefore;
    private LocalDateTime expireAt;
    private Duration validityDuration;
    private Duration overlapDuration;

    public AuthenticationTokenInfoBuilder username(String username) {
      this.username = username;
      return this;
    }

    public AuthenticationTokenInfoBuilder roles(List<String> roles) {
      this.roles = roles;
      return this;
    }

    public AuthenticationTokenInfoBuilder issuedAt(LocalDateTime issuedAt) {
      this.issuedAt = issuedAt;
      return this;
    }

    public AuthenticationTokenInfoBuilder notBefore(LocalDateTime notBefore) {
      this.notBefore = notBefore;
      return this;
    }

    public AuthenticationTokenInfoBuilder expireAt(LocalDateTime expireAt) {
      this.expireAt = expireAt;
      return this;
    }

    public AuthenticationTokenInfoBuilder validityDuration(Duration validityDuration) {
      this.validityDuration = validityDuration;
      return this;
    }

    public AuthenticationTokenInfoBuilder overlapDuration(Duration overlapDuration) {
      this.overlapDuration = overlapDuration;
      return this;
    }

    public AuthenticationTokenInfo build() {
      AuthenticationTokenInfo authenticationTokenInfo = new AuthenticationTokenInfo();
      authenticationTokenInfo.setUsername(username);
      authenticationTokenInfo.setRoles(roles);
      authenticationTokenInfo.setIssuedAt(issuedAt);
      authenticationTokenInfo.setNotBefore(notBefore);
      authenticationTokenInfo.setExpireAt(expireAt);
      authenticationTokenInfo.setValidityDuration(validityDuration);
      authenticationTokenInfo.setOverlapDuration(overlapDuration);
      return authenticationTokenInfo;
    }
  }
}
