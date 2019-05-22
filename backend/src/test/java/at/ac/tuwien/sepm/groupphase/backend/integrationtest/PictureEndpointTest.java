package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class PictureEndpointTest extends BaseIntegrationTest {


  private static final String NEWS_PICTURES_ENDPOINT = "/newspictures";
  private static final String SPECIFIC_PICTURE_PATH = "/{id}";

  @Autowired private PictureRepository pictureRepository;

  private static Picture P1;

  private static PictureDto PDTO1;

  @Before
  public void initialize() {
    PDTO1 = new PictureDto();
    PDTO1.setData(new byte[1]);

    P1 = new Picture();
    P1.setData(new byte[1]);

    P1 = pictureRepository.save(P1);
  }

  @After
  public void cleanUp() {
    pictureRepository.deleteAll();
  }

  @Test
  public void givenAdminAndPicture_whenFindPictureWithId_ThenReturnPictureAndOk() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(NEWS_PICTURES_ENDPOINT + SPECIFIC_PICTURE_PATH, P1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.as(PictureDto.class).getId()).isEqualTo(P1.getId());
  }

  @Test
  public void givenAdminAndPicture_whenFindPictureWithUnknownId_ThenReturnNotFound() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(NEWS_PICTURES_ENDPOINT + SPECIFIC_PICTURE_PATH, -1L)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void givenAnonymous_whenFindPictureWithId_ThenReturnNotFound() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(NEWS_PICTURES_ENDPOINT + SPECIFIC_PICTURE_PATH, -1L)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void givenAdmin_whenCreatePicture_ThenReturnUnauthorized() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(PDTO1)
            .when()
            .post(NEWS_PICTURES_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  public void givenAnonymous_whenCreatePicture_ThenReturnUnauthorized() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(PDTO1)
            .when()
            .post(NEWS_PICTURES_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  public void givenUser_whenCreatePicture_ThenReturnForbidden() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(PDTO1)
            .when()
            .post(NEWS_PICTURES_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

}
