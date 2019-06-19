package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.hall.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.location.LocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;


public class HallEndpointTest extends BaseIntegrationTest {

  private static final String HALL_ENDPOINT = "/halls";
  private static final String SPECIFIC_HALL_PATH = HALL_ENDPOINT + "/{id}";
  private static final String SPECIFIC_HALL_UNITS = SPECIFIC_HALL_PATH + "/units";

  @Autowired private LocationRepository locationRepository;
  @Autowired private HallRepository hallRepository;
  @Autowired private UnitRepository unitRepository;

  @Autowired private HallMapper hallMapper;

  private static Location location_1;

  private static Hall hall_1;

  private static Unit unit_1_of_hall_1;
  private static Unit unit_2_of_hall_1;

  @Before
  public void setUp() {
    location_1 = new Location.Builder()
        .name("test location")
        .street("test street")
        .place("test place")
        .postalCode("1234")
        .country("Austria")
        .build();
    location_1 = locationRepository.save(location_1);



    hall_1 = new Hall.Builder()
        .name("hall 1")
        .version(1)
        .boundaryPoint(new Point.Builder().coordinateX(1).coordinateY(3).build())
        .location(location_1)
        .build();
    hall_1 = hallRepository.save(hall_1);

    hall_1 = new Hall.Builder()
        .name("hall 1")
        .version(1)
        .boundaryPoint(new Point.Builder().coordinateX(1).coordinateY(3).build())
        .location(location_1)
        .build();
    hall_1 = hallRepository.save(hall_1);



    unit_1_of_hall_1 = new Unit.Builder()
        .name("seat 1")
        .capacity(1)
        .upperBoundary(new Point.Builder().coordinateX(1).coordinateY(1).build())
        .lowerBoundary(new Point.Builder().coordinateX(1).coordinateY(1).build())
        .hall(hall_1)
        .build();
    unit_1_of_hall_1 = unitRepository.save(unit_1_of_hall_1);

    unit_2_of_hall_1 = new Unit.Builder()
        .name("seat 2")
        .capacity(1)
        .upperBoundary(new Point.Builder().coordinateX(1).coordinateY(2).build())
        .lowerBoundary(new Point.Builder().coordinateX(1).coordinateY(2).build())
        .hall(hall_1)
        .build();
    unit_2_of_hall_1 = unitRepository.save(unit_2_of_hall_1);
  }

  @After
  public void cleanUp() {
    unitRepository.deleteAll();
    hallRepository.deleteAll();
    locationRepository.deleteAll();
  }



  // TEST GET ONE BY ID

  @Test
  public void givenAdmin_whenFindOneHallById_thenReturnHallAnd200() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_HALL_PATH, hall_1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.as(HallDto.class)).isEqualTo(hallMapper.hallToHallDto(hall_1));
  }

  @Test
  public void givenUser_whenFindOneHallById_then403() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(SPECIFIC_HALL_PATH, hall_1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void givenAdmin_whenFindOneHallByUnknownId_then404() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_HALL_PATH, -1L)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }



  // TEST GET UNITS BY HALL ID

  @Test
  public void givenAdmin_whenFindUnitsByHallId_thenReturnUnitsAnd200() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_HALL_UNITS, hall_1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

    List<UnitDto> responseList = Arrays.asList(response.as(UnitDto[].class));
    assertThat(responseList.size()).isEqualTo(2);
  }

  @Test
  public void givenUser_whenFindUnitsByHallId_then403() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(SPECIFIC_HALL_UNITS, hall_1.getId())
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void givenAdmin_whenFindUnitsByUnknownHallId_then404() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_HALL_UNITS, -1L)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }



  // TEST SAVE HALL



  // TEST UPDATE HALL
}
