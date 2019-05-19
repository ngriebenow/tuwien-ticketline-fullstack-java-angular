package at.ac.tuwien.sepm.groupphase.backend.integrationtest.base;

import at.ac.tuwien.sepm.groupphase.backend.configuration.JacksonConfiguration;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthenticationConstants;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import at.ac.tuwien.sepm.groupphase.backend.service.implementation.SimpleHeaderTokenAuthenticationService;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import org.assertj.core.util.Strings;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class BaseIntegrationTest {

  private static final String SERVER_HOST = "http://localhost";
  private static final String USER_USERNAME = "user";
  private static final String USER_PASSWORD = "password";
  private static final String ADMIN_PASSWORD = "password";
  private static final String ADMIN_USERNAME = "admin";
  protected String validUserTokenWithPrefix;
  protected String validAdminTokenWithPrefix;

  @Value("${server.context-path}")
  private String contextPath;

  @LocalServerPort private int port;
  @Autowired private SimpleHeaderTokenAuthenticationService simpleHeaderTokenAuthenticationService;
  @Autowired private JacksonConfiguration jacksonConfiguration;

  @Autowired private AccountService accountService;
  @Autowired private UserRepository userRepository;

  @Before
  public void beforeBase() {
    if (!userRepository.existsByUsername(ADMIN_USERNAME)) {
      UserDto admin = new UserDto();
      admin.setUsername(ADMIN_USERNAME);
      admin.setPassword(ADMIN_PASSWORD);
      admin.setFailedLoginCounter(0);
      admin.setEnabled(true);
      admin.setAdmin(true);
      accountService.saveUser(admin);
    }

    if (!userRepository.existsByUsername(USER_USERNAME)) {
      UserDto user = new UserDto();
      user.setUsername(USER_USERNAME);
      user.setPassword(USER_PASSWORD);
      user.setFailedLoginCounter(0);
      user.setEnabled(true);
      user.setAdmin(false);
      accountService.saveUser(user);
    }

    RestAssured.baseURI = SERVER_HOST;
    RestAssured.basePath = contextPath;
    RestAssured.port = port;
    RestAssured.config =
        RestAssuredConfig.config()
            .objectMapperConfig(
                new ObjectMapperConfig()
                    .jackson2ObjectMapperFactory(
                        (aClass, s) -> jacksonConfiguration.jackson2ObjectMapperBuilder().build()));
    validUserTokenWithPrefix =
        Strings.join(
                AuthenticationConstants.TOKEN_PREFIX,
                simpleHeaderTokenAuthenticationService
                    .authenticate(USER_USERNAME, USER_PASSWORD)
                    .getCurrentToken())
            .with(" ");
    validAdminTokenWithPrefix =
        Strings.join(
                AuthenticationConstants.TOKEN_PREFIX,
                simpleHeaderTokenAuthenticationService
                    .authenticate(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .getCurrentToken())
            .with(" ");
  }
}
