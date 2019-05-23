package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventSearchResultMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceSearchResultMapper;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.awt.Color;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class EventEndpointTest extends BaseIntegrationTest {

  private static final String EVENT_ENDPOINT = "/events";
  private static final String SPECIFIC_EVENT_PATH = "/{id}";
  private static final String EVENT_PERFORMANCE_ENDPOINT = "/performances";

  @Autowired private PerformanceRepository performanceRepository;
  @Autowired private HallRepository hallRepository;
  @Autowired private LocationRepository locationRepository;
  @Autowired private ArtistRepository artistRepository;
  @Autowired private EventRepository eventRepository;
  @Autowired private PriceCategoryRepository priceCategoryRepository;

  @Autowired private EventMapper eventMapper;
  @Autowired private PerformanceSearchResultMapper performanceSearchResultMapper;
  @Autowired private EventSearchResultMapper eventSearchResultMapper;

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

  private static int PRICE_TOLERANCE = 1000;


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
            .location(L1)
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
            .startAt(LocalDateTime.of(2000, 1, 15,0,0))
            .id(0L)
            .event(E1)
            .build();
    P1 = performanceRepository.save(P1);

    P2 =
        new Performance.Builder()
            .name("2A")
            .startAt(LocalDateTime.of(2000, 1, 15,8,0))
            .id(1L)
            .event(E1)
            .build();
    P2 = performanceRepository.save(P2);

    P3 =
        new Performance.Builder()
            .name("1B")
            .startAt(LocalDateTime.of(2000, 2, 15,16,0))
            .event(E2)
            .build();
    P3 = performanceRepository.save(P3);

    P4 =
        new Performance.Builder()
            .name("2B")
            .startAt(LocalDateTime.of(2000, 2, 15,23,0))
            .event(E2)
            .build();
    P4 = performanceRepository.save(P4);

    P5 =
        new Performance.Builder()
            .name("3B")
            .startAt(LocalDateTime.of(2000, 3, 15,0,0))
            .event(E2)
            .build();
    P5 = performanceRepository.save(P5);

    P6 =
        new Performance.Builder()
            .name("1C")
            .startAt(LocalDateTime.of(2000, 3, 15,8,29))
            .event(E3)
            .build();
    P6 = performanceRepository.save(P6);

    P7 =
        new Performance.Builder()
            .name("2C")
            .startAt(LocalDateTime.of(2000, 6, 14,16,30))
            .event(E3)
            .build();
    P7 = performanceRepository.save(P7);

    P8 =
        new Performance.Builder()
            .name("3C")
            .startAt(LocalDateTime.of(2000, 6, 15,22,31))
            .event(E3)
            .build();
    P8 = performanceRepository.save(P8);

    P9 =
        new Performance.Builder()
            .name("4C")
            .startAt(LocalDateTime.of(2000, 12, 15,23,59))
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
    
    E1_SR = eventSearchResultMapper.eventToEventSearchResultDto(E1);
    E1_SR.setPriceRange("20 €");
    E2_SR = eventSearchResultMapper.eventToEventSearchResultDto(E2);
    E2_SR.setPriceRange("30 - 40 €");
    E3_SR = eventSearchResultMapper.eventToEventSearchResultDto(E3);
    E3_SR.setPriceRange("50 - 70 €");
  }

  @After
  public void cleanUp() {
    priceCategoryRepository.deleteAll();
    performanceRepository.deleteAll();
    eventRepository.deleteAll();
    hallRepository.deleteAll();
    locationRepository.deleteAll();
    artistRepository.deleteAll();
  }

  @Test
  public void givenNoEvent_whenFindInvalidEvent_ThenReturnNotFound() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + SPECIFIC_EVENT_PATH, 0)
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  public void givenEvent_whenFindByEvent_thenReturnEvent() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + SPECIFIC_EVENT_PATH, E1.getId())
            .then()
            .extract()
            .response();

    Event retE1 = eventMapper.eventDtoToEvent(response.as(EventDto.class));
    Assert.assertThat(retE1, is(equalTo(E1)));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEventId_whenFindPerformancesByEventId_thenReturnPerformances() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + SPECIFIC_EVENT_PATH + EVENT_PERFORMANCE_ENDPOINT, E1.getId())
            .then()
            .extract()
            .response();

    List<PerformanceSearchResultDto> retList = Arrays.asList(response.as(PerformanceSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(2));
    Assert.assertTrue(retList.contains(
        performanceSearchResultMapper.performanceToPerformanceSearchResultDto(P1)
    ));
    Assert.assertTrue(retList.contains(
        performanceSearchResultMapper.performanceToPerformanceSearchResultDto(P2)
    ));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenInvalidEventId_whenFindPerformancesByEventId_thenThrowNotFoundException() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + SPECIFIC_EVENT_PATH + EVENT_PERFORMANCE_ENDPOINT, -1)
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  public void givenEvents_whenFilterByEventName_thenReturnEvents() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?name=d")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(2));
    Assert.assertTrue(retList.contains(E2_SR));
    Assert.assertTrue(retList.contains(E3_SR));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByEventCategory_thenReturnEvents() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?eventCategory=CINEMA")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(1));
    Assert.assertTrue(retList.contains(E1_SR));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByArtistName_thenReturnEvents() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?artistName=y")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(1));
    Assert.assertTrue(retList.contains(E2_SR));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByArtistSurname_thenReturnEvents() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?artistName=FG")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(2));
    Assert.assertTrue(retList.contains(E2_SR));
    Assert.assertTrue(retList.contains(E3_SR));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByPrice0_thenReturnNoEvents() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?priceInCents=0")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(0));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByPrice1000_thenReturnE1() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?priceInCents=1000")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(1));
    Assert.assertTrue(retList.contains(E1_SR));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByPrice4000_thenReturnE2E3() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?priceInCents=4000")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(2));
    Assert.assertTrue(retList.contains(E2_SR));
    Assert.assertTrue(retList.contains(E3_SR));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByPrice8000_thenReturnE3() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?priceInCents=8000")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(1));
    Assert.assertTrue(retList.contains(E3_SR));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByPrice8001_thenReturnNoEvents() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?priceInCents=8001")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(0));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

  @Test
  public void givenEvents_whenFilterByTime0000_thenReturnE1P1E2P5() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + "?startAtTime=00-00")
            .then()
            .extract()
            .response();


    List<EventSearchResultDto> retList = Arrays.asList(response.as(EventSearchResultDto[].class));

    Assert.assertThat(retList.size(),is(2));

    EventSearchResultDto E1sR = E1_SR;
    EventSearchResultDto E2sR = E2_SR;

    EventSearchResultDto retE1sR = retList.get(retList.indexOf(E1sR));
    EventSearchResultDto retE2sR = retList.get(retList.indexOf(E2sR));

    Assert.assertThat(retE1sR,equalTo(E1sR));
    Assert.assertThat(retE2sR,equalTo(E2sR));
    
    Assert.assertThat(retE1sR.getPerformances().size(),is(1));
    Assert.assertThat(retE2sR.getPerformances().size(),is(1));
    
    PerformanceSearchResultDto retP1sR = retE1sR.getPerformances().get(0);
    PerformanceSearchResultDto retP2sR = retE2sR.getPerformances().get(0);
    
    PerformanceSearchResultDto P1sR = performanceSearchResultMapper.performanceToPerformanceSearchResultDto(P1);
    PerformanceSearchResultDto P5sR = performanceSearchResultMapper.performanceToPerformanceSearchResultDto(P5);

    Assert.assertThat(retP1sR,equalTo(P1sR));
    Assert.assertThat(retP2sR,equalTo(P5sR));

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
  }

}
