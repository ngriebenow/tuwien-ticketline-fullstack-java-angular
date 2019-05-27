package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsReadRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class NewsEndpointTest extends BaseIntegrationTest {

  private static final String NEWS_ENDPOINT = "/news";
  private static final String SPECIFIC_NEWS_PATH = "/{id}";

  @Autowired
  private NewsRepository newsRepository;
  @Autowired
  private NewsReadRepository newsReadRepository;

  private static News N1;

  @Before
  public void initialize() {
    N1 = new News.Builder()
        .id(0L)
        .publishedAt(LocalDateTime.now())
        .title("Test")
        .summary("Just one Test")
        .text("abcdefghijk test news test news news")
        .build();

    N1 = newsRepository.save(N1);
  }

  @After
  public void cleanUp() {
    newsReadRepository.deleteAll();
    newsRepository.deleteAll();
  }

  @Test
  public void givenAdminAndNews_whenFindOneNewsWithId_ThenReturnNewsAndOk() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(NEWS_ENDPOINT + SPECIFIC_NEWS_PATH, N1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.as(DetailedNewsDto.class).getId()).isEqualTo(N1.getId());
  }

  @Test
  public void givenAdminAndNews_whenFindOneNewsWithUnknownId_ThenReturnNotFound() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(NEWS_ENDPOINT + SPECIFIC_NEWS_PATH, -1L)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());

  }

  @Test
  public void givenAnonymous_whenFindOneNewsWithId_ThenReturnUnauthorized() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .when()
            .get(NEWS_ENDPOINT + SPECIFIC_NEWS_PATH, -1L)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  public void givenAdmin_whenPublishNews_ThenReturnCreated() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(
                DetailedNewsDto.builder()
                    .id(1L)
                    .title("ADMIN")
                    .text("text text text")
                    .summary("summary")
                    .build())
            .when()
            .post(NEWS_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
  }


  @Test
  public void givenAnonymous_whenPublishNews_ThenReturnUnauthorized() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(
                DetailedNewsDto.builder()
                    .id(1L)
                    .title("Test")
                    .text("text text text")
                    .summary("summary")
                    .build())
            .when()
            .post(NEWS_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  public void givenUser_whenPublishNews_ThenReturnForbidden() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(
                DetailedNewsDto.builder()
                    .id(1L)
                    .title("Test")
                    .text("text text text")
                    .summary("summary")
                    .build())
            .when()
            .post(NEWS_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

}
