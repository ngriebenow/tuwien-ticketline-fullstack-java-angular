package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.hamcrest.core.Is.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class UserAuthorityTest extends BaseIntegrationTest {

  private static final String CLIENT_ENDPOINT = "/clients";
  private static final String USER_ENDPOINT = "/users";
  private static final String SPECIFIC_EVENT_PATH = "/{id}";

  @Autowired
  private ClientRepository clientRepository;

  private Client testClient;

  @Before
  public void setUp() {
    testClient = new Client();
    testClient.setName("Test");
    testClient.setSurname("Client");
    testClient.setEmail("test@client.com");
    testClient = clientRepository.saveAndFlush(testClient);
  }

  @After
  public void cleanUp() {
    clientRepository.deleteAll();
  }

  @Test
  public void whenNotLoggedIn_thenStatusUnauthorized() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .when()
            .get(USER_ENDPOINT + SPECIFIC_EVENT_PATH, 1)
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED.value()));
  }

  @Test
  public void whenPrivilegesTooLow_thenStatusForbidden() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(USER_ENDPOINT + SPECIFIC_EVENT_PATH, 1)
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  public void whenPrivilegesOk_thenStatusOk() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(USER_ENDPOINT + SPECIFIC_EVENT_PATH, "admin")
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void whenAccessingUserContentWithAdminPrivileges_thenStatusOk() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(CLIENT_ENDPOINT + SPECIFIC_EVENT_PATH, testClient.getId())
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void whenAccessingUserContentWithUserPrivileges_thenStatusOk() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(CLIENT_ENDPOINT + SPECIFIC_EVENT_PATH, testClient.getId())
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }
}
