package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PointDto;
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
import java.util.ArrayList;
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
  @Autowired private LocationMapper locationMapper;
  @Autowired private HallMapper hallMapper;

  private static Location location_1;

  private static Hall hall_1;
  private static HallRequestDto hall_1_update;
  private static HallRequestDto hall_to_save;
  private static HallRequestDto hall_without_name;
  private static HallRequestDto hall_without_boundaryPoint;
  private static HallRequestDto hall_without_location;

  private static Unit unit_1_of_hall_1;
  private static Unit unit_2_of_hall_1;
  private static UnitDto unit_1_sitz;
  private static UnitDto unit_2_sitz;
  private static UnitDto unit_3_sector;
  private static UnitDto unit_4_sitz_overlapping_unit_1;
  private static UnitDto unit_5_sitz_overlapping_unit_3;
  private static UnitDto unit_6_sector_overlapping_unit_3;
  private static UnitDto unit_7_sitz_out_of_bound;

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
        .boundaryPoint(new Point.Builder().coordinateX(1).coordinateY(2).build())
        .location(location_1)
        .build();
    hall_1 = hallRepository.save(hall_1);

    hall_1_update = new HallRequestDto.Builder()
        .id(hall_1.getId())
        .name("hall 1")
        .version(1)
        .boundaryPoint(new PointDto.Builder().coordinateX(10).coordinateY(10).build())
        .location(locationMapper.locationToLocationDto(location_1))
        .build();

    hall_to_save = new HallRequestDto.Builder()
        .name("hall to save")
        .boundaryPoint(new PointDto.Builder().coordinateX(10).coordinateY(10).build())
        .location(locationMapper.locationToLocationDto(location_1))
        .build();



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

    unit_1_sitz = new UnitDto.Builder()
        .name("test name")
        .capacity(1)
        .upperBoundary(new PointDto.Builder().coordinateX(3).coordinateY(2).build())
        .lowerBoundary(new PointDto.Builder().coordinateX(3).coordinateY(2).build())
        .build();

    unit_2_sitz = new UnitDto.Builder()
        .name("test name")
        .capacity(1)
        .upperBoundary(new PointDto.Builder().coordinateX(4).coordinateY(2).build())
        .lowerBoundary(new PointDto.Builder().coordinateX(4).coordinateY(2).build())
        .build();

    unit_3_sector = new UnitDto.Builder()
        .name("test name")
        .capacity(5)
        .upperBoundary(new PointDto.Builder().coordinateX(4).coordinateY(4).build())
        .lowerBoundary(new PointDto.Builder().coordinateX(9).coordinateY(5).build())
        .build();

    unit_4_sitz_overlapping_unit_1 = new UnitDto.Builder()
        .name("test name")
        .capacity(1)
        .upperBoundary(new PointDto.Builder().coordinateX(3).coordinateY(2).build())
        .lowerBoundary(new PointDto.Builder().coordinateX(3).coordinateY(2).build())
        .build();

    unit_5_sitz_overlapping_unit_3 = new UnitDto.Builder()
        .name("test name")
        .capacity(1)
        .upperBoundary(new PointDto.Builder().coordinateX(5).coordinateY(5).build())
        .lowerBoundary(new PointDto.Builder().coordinateX(5).coordinateY(5).build())
        .build();

    unit_6_sector_overlapping_unit_3 = new UnitDto.Builder()
        .name("test name")
        .capacity(5)
        .upperBoundary(new PointDto.Builder().coordinateX(7).coordinateY(3).build())
        .lowerBoundary(new PointDto.Builder().coordinateX(7).coordinateY(6).build())
        .build();

    unit_7_sitz_out_of_bound = new UnitDto.Builder()
        .name("test name")
        .capacity(1)
        .upperBoundary(new PointDto.Builder().coordinateX(20).coordinateY(20).build())
        .lowerBoundary(new PointDto.Builder().coordinateX(20).coordinateY(20).build())
        .build();
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



  // TEST UPDATE HALL

  @Test
  public void givenAdmin_whenUpdateHall_thenReturnUpdatedHallWithIncreasedVersionAnd200() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_1_sitz);
    unit_list.add(unit_2_sitz);
    unit_list.add(unit_3_sector);
    hall_1_update.setUnits(unit_list);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_1_update)
            .when()
            .put(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.as(HallDto.class).getName()).isEqualTo(hall_1_update.getName());
    assertThat(response.as(HallDto.class).getId()).isNotEqualTo(hall_1_update.getId());
    assertThat(response.as(HallDto.class).getVersion()).isEqualTo(hall_1_update.getVersion() + 1);
  }

  @Test
  public void givenUser_whenUpdateHall_then403() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(hall_1_update)
            .when()
            .put(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }



  // TEST SAVE HALL

  @Test
  public void givenAdmin_whenSaveHall_thenReturnHallWithVersion1And201() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_1_sitz);
    unit_list.add(unit_2_sitz);
    unit_list.add(unit_3_sector);
    hall_to_save.setUnits(unit_list);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.as(HallDto.class).getName()).isEqualTo(hall_to_save.getName());
    assertThat(response.as(HallDto.class).getVersion()).isEqualTo(1);
    assertThat(response.as(HallDto.class).getLocation().getId()).isEqualTo(hall_to_save.getLocation().getId());
  }

  @Test
  public void givenUser_whenSaveHall_then403() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }

  @Test
  public void givenAdmin_whenSaveHallWithSeatOverlappingSeat_then400() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_1_sitz);
    unit_list.add(unit_4_sitz_overlapping_unit_1);
    hall_to_save.setUnits(unit_list);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void givenAdmin_whenSaveHallWithSeatOverlappingSector_then400() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_3_sector);
    unit_list.add(unit_5_sitz_overlapping_unit_3);
    hall_to_save.setUnits(unit_list);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void givenAdmin_whenSaveHallWithSectorOverlappingSector_then400() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_3_sector);
    unit_list.add(unit_6_sector_overlapping_unit_3);
    hall_to_save.setUnits(unit_list);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void givenAdmin_whenSaveHallWithSeatOutOfHallSize_then400() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_7_sitz_out_of_bound);
    hall_to_save.setUnits(unit_list);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  public void givenAdmin_whenSaveHallWithoutName_then400() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_1_sitz);
    hall_to_save.setUnits(unit_list);

    hall_to_save.setName("");

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  public void givenAdmin_whenSaveHallWithoutBoundaryPoint_then400() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_1_sitz);
    hall_to_save.setUnits(unit_list);

    hall_to_save.setBoundaryPoint(null);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  public void givenAdmin_whenSaveHallWithoutLocation_then400() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_1_sitz);
    hall_to_save.setUnits(unit_list);

    hall_to_save.setLocation(null);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  public void givenAdmin_whenSaveHallWithoutUnits_then400() {
    hall_to_save.setUnits(null);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void givenAdmin_whenSaveHallAndGetUnitsOfReturnedHall_thenNumberOfUnitsEqualsGivenUnits() {
    List<UnitDto> unit_list = new ArrayList<>();
    unit_list.add(unit_1_sitz);
    unit_list.add(unit_2_sitz);
    unit_list.add(unit_3_sector);
    hall_to_save.setUnits(unit_list);

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .body(hall_to_save)
            .when()
            .post(HALL_ENDPOINT)
            .then()
            .extract()
            .response();

    Response response2 =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validAdminTokenWithPrefix)
            .when()
            .get(SPECIFIC_HALL_UNITS, response.as(HallDto.class).getId())
            .then()
            .extract()
            .response();

    List<UnitDto> responseList = Arrays.asList(response2.as(UnitDto[].class));
    assertThat(responseList.size()).isEqualTo(3);
  }
}
