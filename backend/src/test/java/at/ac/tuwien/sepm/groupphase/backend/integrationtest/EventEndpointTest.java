package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.DetailedMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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

  @Autowired private EventMapper eventMapper;
  @Autowired private PerformanceMapper performanceMapper;

  private Event E1;
  private Event E2;
  private Event E3;

  private Artist A1;
  private Artist A2;
  private Artist A3;
  private Artist A4;


  private Hall H1;
  private Hall H2;
  private Hall H3;

  private Location L1;
  private Location L2;

  private Performance P1;
  private Performance P2;

  private Performance P3;
  private Performance P4;
  private Performance P5;

  private Performance P6;
  private Performance P7;
  private Performance P8;
  private Performance P9;

  @Before
  public void initialize() {
    E1 = new Event.Builder()
        .name("Abc")
        .category(EventCategory.CINEMA)
        .duration(Duration.ofHours(2))
        .content("Content1")
        .build();

    E2 = new Event.Builder()
        .name("Bcd")
        .category(EventCategory.CONCERT)
        .duration(Duration.ofHours(5))
        .content("Content2")
        .build();

    E3 = new Event.Builder()
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
    E2.setArtists(List.of(A1,A2,A3,A4));
    E3.setArtists(List.of(A2,A4));

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

    P1 = new Performance.Builder()
        .name("1A")
        .startAt(LocalDate.of(2000,1,15).atStartOfDay())
        .id(0L)
        .event(E1)
        .build();
    P1 = performanceRepository.save(P1);

    P2 = new Performance.Builder()
        .name("2A")
        .startAt(LocalDate.of(2000,2,15).atStartOfDay())
        .id(1L)
        .event(E1)
        .build();
    P2 = performanceRepository.save(P2);



    P3 = new Performance.Builder()
        .name("1B")
        .startAt(LocalDate.of(2000,3,15).atStartOfDay())
        .event(E2)
        .build();
    P3 = performanceRepository.save(P3);

    P4 = new Performance.Builder()
        .name("2B")
        .startAt(LocalDate.of(2000,4,15).atStartOfDay())
        .event(E2)
        .build();
    P4 = performanceRepository.save(P4);

    P5 = new Performance.Builder()
        .name("3B")
        .startAt(LocalDate.of(2000,5,15).atStartOfDay())
        .event(E2)
        .build();
    P5 = performanceRepository.save(P5);


    P6 = new Performance.Builder()
        .name("1C")
        .startAt(LocalDate.of(2000,6,15).atStartOfDay())
        .event(E3)
        .build();
    P6 = performanceRepository.save(P6);

    P7 = new Performance.Builder()
        .name("2C")
        .startAt(LocalDate.of(2000,7,15).atStartOfDay())
        .event(E3)
        .build();
    P7 = performanceRepository.save(P7);

    P8 = new Performance.Builder()
        .name("3C")
        .startAt(LocalDate.of(2000,8,15).atStartOfDay())
        .event(E3)
        .build();
    P8 = performanceRepository.save(P8);

    P9 = new Performance.Builder()
        .name("4C")
        .startAt(LocalDate.of(2000,9,15).atStartOfDay())
        .event(E3)
        .build();
    P9 = performanceRepository.save(P9);


  }


  @Test
  public void givenNoEvent_whenFindInvalidEvent_ThenReturnNotFound() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + SPECIFIC_EVENT_PATH,0)
            .then()
            .extract()
            .response();


    Assert.assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }


  @Test
  public void givenEvent_whenFindByEvent_thenReturnEvent() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + SPECIFIC_EVENT_PATH,E1.getId())
            .then()
            .extract()
            .response();

    Event retE1 = eventMapper.eventDtoToEvent(response.as(EventDto.class));
    Assert.assertThat(retE1, is(equalTo(E1)));
  }


  @Test
  public void givenEventId_whenFindPerformancesByEventId_thenReturnPerformances() {

    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + SPECIFIC_EVENT_PATH + EVENT_PERFORMANCE_ENDPOINT,E1.getId())
            .then()
            .extract()
            .response();

    List<PerformanceDto> retList = response.as(List.class);

    List<Performance> performances = new ArrayList<>();
    retList.forEach(p -> performances.add(performanceMapper.performanceDtoToPerformance(p)));

    Assert.assertTrue(performances.contains(P1));
    Assert.assertTrue(performances.contains(P2));
  }

  @Test
  public void givenInvalidEventId_whenFindPerformancesByEventId_thenThrowNotFoundException() {
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when()
            .get(EVENT_ENDPOINT + SPECIFIC_EVENT_PATH + EVENT_PERFORMANCE_ENDPOINT,-1)
            .then()
            .extract()
            .response();

    Assert.assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }

  @Test
  public void givenEvents_whenFilterByEventName_thenReturnEvents() {
/*
    EventFilterDto filterDto = new EventFilterDto();
    filterDto.setName("D");

    List<EventDto> retList = eventService.getEventsFiltered(filterDto,Pageable.unpaged());

    List<Event> events = retList.stream().map(
        e -> eventMapper.eventDtoToEvent(e)).collect(Collectors.toList());

    Assert.assertThat(events.size(),is(2));
    Assert.assertTrue(events.contains(E2));
    Assert.assertTrue(events.contains(E3));
*/
  }

}
