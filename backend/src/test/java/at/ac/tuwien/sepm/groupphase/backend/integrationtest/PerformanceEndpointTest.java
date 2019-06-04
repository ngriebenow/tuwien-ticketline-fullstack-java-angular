package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.hamcrest.core.Is.is;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.definedunit.DefinedUnitMapperImpl;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class PerformanceEndpointTest extends BaseIntegrationTest {

  private static final String PERFORMANCE_ENDPOINT =
      "/performances/hall-viewing/defined-units/{id}";

  @Autowired
  private DefinedUnitRepository definedUnitRepository;
  @Autowired
  private PerformanceRepository performanceRepository;
  @Autowired
  private EventRepository eventRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private LocationRepository locationRepository;
  @Autowired
  private PriceCategoryRepository priceCategoryRepository;
  @Autowired
  private HallRepository hallRepository;
  @Autowired
  private UnitRepository unitRepository;

  @Autowired
  private DefinedUnitMapperImpl definedUnitMapper;

  private static Event E1;
  private static Event E2;
  private static Event E3;

  private static EventSearchResultDto E1_SR;
  private static EventSearchResultDto E2_SR;
  private static EventSearchResultDto E3_SR;

  private static Artist A1;
  private static Artist A2;
  private static Artist A3;
  private static Artist A4;

  private static Hall H1;
  private static Hall H2;
  private static Hall H3;

  private static Location L1;
  private static Location L2;

  private static Performance P1;
  private static Performance P2;

  private static Performance P3;
  private static Performance P4;
  private static Performance P5;

  private static Performance P6;
  private static Performance P7;
  private static Performance P8;
  private static Performance P9;

  private PriceCategory PC1;
  private PriceCategory PC2;

  private PriceCategory PC3;
  private PriceCategory PC4;

  private PriceCategory PC5;
  private PriceCategory PC6;

  private Unit U1;
  private Unit U2;
  private Unit U3;
  private Unit U4;
  private Unit U5;
  private Unit U6;

  private DefinedUnit DU1;
  private DefinedUnit DU2;
  private DefinedUnit DU3;
  private DefinedUnit DU4;
  private DefinedUnit DU5;
  private DefinedUnit DU6;

  @Before
  public void initialize() {
    E1 =
        new Event.Builder()
            .name("Abc")
            .category(EventCategory.CINEMA)
            .duration(Duration.ofHours(2))
            .content("Content1")
            .build();

    E2 =
        new Event.Builder()
            .name("Bcd")
            .category(EventCategory.CONCERT)
            .duration(Duration.ofHours(5))
            .content("Content2")
            .build();

    E3 =
        new Event.Builder()
            .name("Cde")
            .category(EventCategory.OTHER)
            .duration(Duration.ofHours(3))
            .content("Content3")
            .build();

    A1 = new Artist.Builder().surname("Artist Abcd").name("Artist Name W").build();
    A2 = new Artist.Builder().surname("Artist Bcde").name("Artist Name X").build();
    A3 = new Artist.Builder().surname("Artist Cdef").name("Artist Name Y").build();
    A4 = new Artist.Builder().surname("Artist Defg").name("Artist Name Z").build();

    A1 = artistRepository.save(A1);
    A2 = artistRepository.save(A2);
    A3 = artistRepository.save(A3);
    A4 = artistRepository.save(A4);

    E1.setArtists(List.of(A1));
    E2.setArtists(List.of(A1, A2, A3, A4));
    E3.setArtists(List.of(A2, A4));

    L1 =
        new Location.Builder()
            .name("Loc Abc")
            .street("Street 1")
            .postalCode("0000")
            .place("Frankenhausen")
            .country("Austria")
            .build();
    L1 = locationRepository.save(L1);

    L2 =
        new Location.Builder()
            .name("Loc Xyz")
            .street("Street 3")
            .postalCode("1111")
            .place("Steinfurt")
            .country("Germany")
            .build();
    L2 = locationRepository.save(L2);

    H1 =
        new Hall.Builder()
            .version(1)
            .name("Hall Goethe")
            .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
            .location(L1)
            .build();
    hallRepository.save(H1);

    H2 =
        new Hall.Builder()
            .version(1)
            .name("Hall Schiller")
            .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
            .location(L1)
            .build();
    hallRepository.save(H2);

    H3 =
        new Hall.Builder()
            .version(1)
            .name("Hall Wolkenstein")
            .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
            .location(L2)
            .build();
    hallRepository.save(H3);

    E1.setHall(H1);
    E2.setHall(H2);
    E3.setHall(H3);

    eventRepository.save(E1);
    eventRepository.save(E2);
    eventRepository.save(E3);

    P1 =
        new Performance.Builder()
            .name("1A")
            .startAt(LocalDateTime.of(3000, 1, 15, 0, 0))
            .id(0L)
            .event(E1)
            .build();
    P1 = performanceRepository.save(P1);

    P2 =
        new Performance.Builder()
            .name("2A")
            .startAt(LocalDateTime.of(3000, 1, 15, 8, 0))
            .id(1L)
            .event(E1)
            .build();
    P2 = performanceRepository.save(P2);

    P3 =
        new Performance.Builder()
            .name("1B")
            .startAt(LocalDateTime.of(3000, 2, 15, 16, 0))
            .event(E2)
            .build();
    P3 = performanceRepository.save(P3);

    P4 =
        new Performance.Builder()
            .name("2B")
            .startAt(LocalDateTime.of(3001, 2, 15, 23, 0))
            .event(E2)
            .build();
    P4 = performanceRepository.save(P4);

    P5 =
        new Performance.Builder()
            .name("3B")
            .startAt(LocalDateTime.of(3000, 3, 15, 0, 0))
            .event(E2)
            .build();
    P5 = performanceRepository.save(P5);

    P6 =
        new Performance.Builder()
            .name("1C")
            .startAt(LocalDateTime.of(3000, 3, 15, 22, 29))
            .event(E3)
            .build();
    P6 = performanceRepository.save(P6);

    P7 =
        new Performance.Builder()
            .name("2C")
            .startAt(LocalDateTime.of(3000, 2, 14, 22, 30))
            .event(E3)
            .build();
    P7 = performanceRepository.save(P7);

    P8 =
        new Performance.Builder()
            .name("3C")
            .startAt(LocalDateTime.of(3000, 6, 15, 22, 31))
            .event(E3)
            .build();
    P8 = performanceRepository.save(P8);

    P9 =
        new Performance.Builder()
            .name("4C")
            .startAt(LocalDateTime.of(3000, 12, 15, 23, 59))
            .event(E3)
            .build();
    P9 = performanceRepository.save(P9);

    PC1 = new PriceCategory.Builder()
        .name("PC1")
        .color(Color.BLACK)
        .event(E1)
        .priceInCents(2000).build();
    PC1 = priceCategoryRepository.save(PC1);

    PC2 = new PriceCategory.Builder()
        .name("PC2")
        .color(Color.BLACK)
        .event(E2)
        .priceInCents(3000).build();
    PC2 = priceCategoryRepository.save(PC2);

    PC3 = new PriceCategory.Builder()
        .name("PC3")
        .color(Color.BLACK)
        .event(E2)
        .priceInCents(4000).build();
    PC3 = priceCategoryRepository.save(PC3);

    PC4 = new PriceCategory.Builder()
        .name("PC4")
        .color(Color.BLACK)
        .event(E3)
        .priceInCents(5000).build();
    PC4 = priceCategoryRepository.save(PC4);

    PC5 = new PriceCategory.Builder()
        .name("PC5")
        .color(Color.BLACK)
        .event(E3)
        .priceInCents(6000).build();
    PC5 = priceCategoryRepository.save(PC5);

    PC6 = new PriceCategory.Builder()
        .name("PC6")
        .color(Color.BLACK)
        .event(E3)
        .priceInCents(7000).build();
    PC6 = priceCategoryRepository.save(PC6);

    U1 = new Unit.Builder()
        .lowerBoundary(new Point(1, 1))
        .upperBoundary(new Point(1, 1))
        .capacity(1)
        .name("Unit1")
        .hall(H1).build();
    U1 = unitRepository.save(U1);

    U2 = new Unit.Builder()
        .lowerBoundary(new Point(2, 1))
        .upperBoundary(new Point(2, 1))
        .capacity(1)
        .name("Unit2")
        .hall(H1).build();
    U2 = unitRepository.save(U2);

    U3 = new Unit.Builder()
        .lowerBoundary(new Point(1, 2))
        .upperBoundary(new Point(1, 2))
        .capacity(1)
        .name("Unit3")
        .hall(H1).build();
    U3 = unitRepository.save(U3);

    U4 = new Unit.Builder()
        .lowerBoundary(new Point(2, 2))
        .upperBoundary(new Point(2, 2))
        .capacity(1)
        .name("Unit4")
        .hall(H1).build();
    U4 = unitRepository.save(U4);

    U5 = new Unit.Builder()
        .lowerBoundary(new Point(1, 1))
        .upperBoundary(new Point(1, 1))
        .capacity(1)
        .name("Unit1")
        .hall(H2).build();
    U5 = unitRepository.save(U5);

    U6 = new Unit.Builder()
        .lowerBoundary(new Point(2, 1))
        .upperBoundary(new Point(2, 1))
        .capacity(1)
        .name("Unit2")
        .hall(H2).build();
    U6 = unitRepository.save(U6);

    DU1 = new DefinedUnit.Builder()
        .capacityFree(1)
        .performance(P1)
        .priceCategory(PC1)
        .unit(U1).build();
    DU1 = definedUnitRepository.save(DU1);

    DU2 = new DefinedUnit.Builder()
        .capacityFree(0)
        .performance(P1)
        .priceCategory(PC2)
        .unit(U2).build();
    DU2 = definedUnitRepository.save(DU2);

    DU3 = new DefinedUnit.Builder()
        .capacityFree(1)
        .performance(P1)
        .priceCategory(PC3)
        .unit(U3).build();
    DU3 = definedUnitRepository.save(DU3);

    DU4 = new DefinedUnit.Builder()
        .capacityFree(0)
        .performance(P1)
        .priceCategory(PC4)
        .unit(U4).build();
    DU4 = definedUnitRepository.save(DU4);

    DU5 = new DefinedUnit.Builder()
        .capacityFree(0)
        .performance(P2)
        .priceCategory(PC5)
        .unit(U5).build();
    DU5 = definedUnitRepository.save(DU5);

    DU6 = new DefinedUnit.Builder()
        .capacityFree(0)
        .performance(P2)
        .priceCategory(PC6)
        .unit(U6).build();
    DU6 = definedUnitRepository.save(DU6);
  }

  @After
  public void cleanUp() {
    definedUnitRepository.deleteAll();
    unitRepository.deleteAll();
    priceCategoryRepository.deleteAll();
    performanceRepository.deleteAll();
    eventRepository.deleteAll();
    hallRepository.deleteAll();
    locationRepository.deleteAll();
    artistRepository.deleteAll();
  }

  @Test
  public void givenInvalidPerformanceId_whenFindNoDefinedUnits_thenReturnNotFound() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(PERFORMANCE_ENDPOINT, 0)
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  public void givenPerformanceId_whenFindById_thenReturnDefinedUnit() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(PERFORMANCE_ENDPOINT, P1.getId())
            .then()
            .extract()
            .response();

    List<DefinedUnitDto> retList = Arrays.asList(response.as(DefinedUnitDto[].class));

    Assert.assertThat(retList.size(), is(4));
    Assert.assertTrue(retList.contains(definedUnitMapper.definedUnitToDto(DU1)));
    Assert.assertTrue(retList.contains(definedUnitMapper.definedUnitToDto(DU2)));
    Assert.assertTrue(retList.contains(definedUnitMapper.definedUnitToDto(DU3)));
    Assert.assertTrue(retList.contains(definedUnitMapper.definedUnitToDto(DU4)));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }
}
