package at.ac.tuwien.sepm.groupphase.backend.configuration.properties;

import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties("application.authentication")
public class AuthenticationConfigurationProperties {

  @NotNull private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  @NotNull private String secret;
  @NotNull private Duration validityDuration = Duration.of(6L, ChronoUnit.HOURS);
  @NotNull private Duration overlapDuration = Duration.of(3L, ChronoUnit.MINUTES);

  public SignatureAlgorithm getSignatureAlgorithm() {
    return signatureAlgorithm;
  }

  public void setSignatureAlgorithm(String signatureAlgorithm) {
    this.signatureAlgorithm = SignatureAlgorithm.forName(signatureAlgorithm);
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Duration getValidityDuration() {
    return validityDuration;
  }

  public void setValidityDuration(long validityDuration) {
    this.validityDuration = Duration.of(validityDuration, ChronoUnit.HOURS);
  }

  public Duration getOverlapDuration() {
    return overlapDuration;
  }

  public void setOverlapDuration(long overlapDuration) {
    this.overlapDuration = Duration.of(overlapDuration, ChronoUnit.MINUTES);
  }
}
