package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

@ApiModel(
    value = "AuthenticationRequest",
    description = "Data Transfer Objects for Authentication Requests via REST")
public class AuthenticationRequest {

  @ApiModelProperty(required = true, name = "The unique name of the user", example = "admin")
  private String username;

  @ApiModelProperty(required = true, name = "The password of the user", example = "password")
  private String password;

  public static AuthenticationRequestBuilder builder() {
    return new AuthenticationRequestBuilder();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public CharSequence getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "AuthenticationRequest{"
        + "username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
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
    AuthenticationRequest that = (AuthenticationRequest) obj;
    return Objects.equals(username, that.username) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  public static final class AuthenticationRequestBuilder {

    private String username;
    private String password;

    public AuthenticationRequestBuilder username(String username) {
      this.username = username;
      return this;
    }

    public AuthenticationRequestBuilder password(String password) {
      this.password = password;
      return this;
    }

    /**
     * Build AuthenticationRequest and return it.
     * @return AuthenticationRequest
     */
    public AuthenticationRequest build() {
      AuthenticationRequest authenticationRequest = new AuthenticationRequest();
      authenticationRequest.setUsername(username);
      authenticationRequest.setPassword(password);
      return authenticationRequest;
    }
  }
}
