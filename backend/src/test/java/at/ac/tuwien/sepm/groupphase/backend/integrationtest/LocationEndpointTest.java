package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class LocationEndpointTest extends BaseIntegrationTest {

  private static final String LOCATION_ENDPOINT = "/locations";
  private static final String SPECIFIC_LOCATION_PATH = LOCATION_ENDPOINT + "/{id}";
  private static final String SPECIFIC_LOCATIONS_HALLS = SPECIFIC_LOCATION_PATH + "/halls";

  @Autowired private LocationRepository locationRepository;
  @Autowired private HallRepository hallRepository;

  private static Location location_1;
  private static Location location_2;
  private static Location location_empty;

  private static Hall hall_of_location_1;

  @Before
  public void setUp() {
    location_1 = new Location.Builder()
        .id(1L)
        .name("TestName")
        .street("TestStreet")
        .place("TestPlace")
        .postalCode("1234")
        .country("TestCountry")
        .build();
    location_1 = locationRepository.save(location_1);

    location_2 = new Location.Builder()
        .name("Test")
        .street("Test")
        .place("Test")
        .postalCode("123")
        .country("Test")
        .build();
    location_2 = locationRepository.save(location_2);

    location_empty = new Location.Builder()
        .name("")
        .street("")
        .place("")
        .postalCode("")
        .country("")
        .build();

    hall_of_location_1 = new Hall.Builder()
        .name("TestHall")
        .version(1)
        .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
        .location(location_1)
        .build();
    hall_of_location_1 = hallRepository.save(hall_of_location_1);
  }

  @After
  public void cleanUp() {
    hallRepository.deleteAll();
    locationRepository.deleteAll();
  }



  // TEST GET ONE BY ID

  @Test
  public void givenAdmin_whenFindOneLocationWithId_thenReturnLocationAn200() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_LOCATION_PATH, location_1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.as(LocationDto.class).getId()).isEqualTo(location_1.getId());
    assertThat(response.as(LocationDto.class).getName()).isEqualTo(location_1.getName());
    assertThat(response.as(LocationDto.class).getStreet()).isEqualTo(location_1.getStreet());
    assertThat(response.as(LocationDto.class).getPostalCode()).isEqualTo(location_1.getPostalCode());
    assertThat(response.as(LocationDto.class).getPlace()).isEqualTo(location_1.getPlace());
    assertThat(response.as(LocationDto.class).getCountry()).isEqualTo(location_1.getCountry());
  }

  @Test
  public void givenUser_whenFindOneLocationWithId_then403() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(SPECIFIC_LOCATION_PATH, location_1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void givenAdmin_whenFindOneLocationWithUnknownId_then404() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_LOCATION_PATH, -1L)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }



  // TEST POST

  @Test
  public void givenAdmin_whenPublishLocation_thenReturnLocationAnd201() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(location_2)
            .when()
            .post(LOCATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.as(LocationDto.class).getId()).isNotNull();
    assertThat(response.as(LocationDto.class).getName()).isEqualTo(location_2.getName());
    assertThat(response.as(LocationDto.class).getStreet()).isEqualTo(location_2.getStreet());
    assertThat(response.as(LocationDto.class).getPostalCode()).isEqualTo(location_2.getPostalCode());
    assertThat(response.as(LocationDto.class).getPlace()).isEqualTo(location_2.getPlace());
    assertThat(response.as(LocationDto.class).getCountry()).isEqualTo(location_2.getCountry());
  }

  @Test
  public void givenUser_whenPublishLocation_then403() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(location_2)
            .when()
            .post(LOCATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void givenAdmin_whenPublishInvalidLocation_then400() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(location_empty)
            .when()
            .post(LOCATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }



  // TEST GET HALLS

  @Test
  public void givenAdmin_whenFindHallsOfLocationWithId_then200() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_LOCATIONS_HALLS, location_1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void givenUser_whenFindHallsOfLocationWithId_then403() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(SPECIFIC_LOCATIONS_HALLS, location_1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void givenAdmin_whenFindHallsOfLocationWithIdWithoutHalls_then404() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_LOCATIONS_HALLS, location_2.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }



  // TEST GET FILTERED

  @Test
  public void givenAdmin_whenFindLocationsFiltered_thenReturnAllAnd200() {
    Map<String, String> params = new HashMap<>();
    params.put("page", "0");
    params.put("count", "20");

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .params(params)
            .when()
            .get(LOCATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

    List<LocationDto> responseList = Arrays.asList(response.as(LocationDto[].class));
    assertThat(responseList.size()).isEqualTo(2);
  }

  @Test
  public void givenUser_whenFindLocationsFiltered_then403() {
    Map<String, String> params = new HashMap<>();
    params.put("page", "0");
    params.put("count", "20");

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .params(params)
            .when()
            .get(LOCATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void givenAdmin_whenFindLocationsFilteredForNonExisting_thenReturnNoneAnd200() {
    Map<String, String> params = new HashMap<>();
    params.put("name", "Non_Existing_Location");
    params.put("page", "0");
    params.put("count", "20");

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .params(params)
            .when()
            .get(LOCATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

    List<LocationDto> responseList = Arrays.asList(response.as(LocationDto[].class));
    assertThat(responseList.size()).isEqualTo(0);
  }
}
