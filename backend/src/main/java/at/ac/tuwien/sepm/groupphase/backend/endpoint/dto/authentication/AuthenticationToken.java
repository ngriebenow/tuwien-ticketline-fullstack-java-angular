package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

@ApiModel(
    value = "AuthenticationToken",
    description = "Data Transfer Objects for AuthenticationTokens")
public class AuthenticationToken {

  @ApiModelProperty(required = true, readOnly = true, name = "Current authentication token")
  private String currentToken;

  @ApiModelProperty(required = true, readOnly = true, name = "Future authentication token")
  private String futureToken;

  public static AuthenticationTokenBuilder builder() {
    return new AuthenticationTokenBuilder();
  }

  public String getCurrentToken() {
    return currentToken;
  }

  public void setCurrentToken(String currentToken) {
    this.currentToken = currentToken;
  }

  public String getFutureToken() {
    return futureToken;
  }

  public void setFutureToken(String futureToken) {
    this.futureToken = futureToken;
  }

  @Override
  public String toString() {
    return "AuthenticationToken{"
        + "currentToken='"
        + currentToken
        + '\''
        + ", futureToken='"
        + futureToken
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
    AuthenticationToken that = (AuthenticationToken) obj;
    return Objects.equals(currentToken, that.currentToken)
        && Objects.equals(futureToken, that.futureToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentToken, futureToken);
  }

  public static final class AuthenticationTokenBuilder {

    private String currentToken;
    private String futureToken;

    public AuthenticationTokenBuilder currentToken(String currentToken) {
      this.currentToken = currentToken;
      return this;
    }

    public AuthenticationTokenBuilder futureToken(String futureToken) {
      this.futureToken = futureToken;
      return this;
    }

    /**
     * Build new AuthenticationToken and return it.
     * @return AuthenticationToken
     */
    public AuthenticationToken build() {
      AuthenticationToken authenticationToken = new AuthenticationToken();
      authenticationToken.setCurrentToken(currentToken);
      authenticationToken.setFutureToken(futureToken);
      return authenticationToken;
    }
  }
}
