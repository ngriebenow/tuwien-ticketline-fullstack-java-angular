package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class InvoiceEndpointTest extends BaseIntegrationTest {

  private static final String RESERVATION_ENDPOINT = "/invoices/reserve";

  private ReservationRequestDto reservationRequestDto;
  private List<TicketRequestDto> ticketRequestDtoList;

  @Autowired private ClientRepository clientRepository;
  @Autowired private LocationRepository locationRepository;
  @Autowired private HallRepository hallRepository;
  @Autowired private ArtistRepository artistRepository;
  @Autowired private EventRepository eventRepository;
  @Autowired private UnitRepository unitRepository;
  @Autowired private PerformanceRepository performanceRepository;
  @Autowired private PriceCategoryRepository priceCategoryRepository;
  @Autowired private DefinedUnitRepository definedUnitRepository;
  @Autowired private TicketRepository ticketRepository;
  @Autowired private InvoiceRepository invoiceRepository;

  private Client client;
  private Location location;
  private Artist artist;
  private Hall hall;
  private Event event;
  private Unit unit1;
  private Unit unit2;
  private Performance performance1;
  private Performance performance2;
  private PriceCategory priceCategory1;
  private PriceCategory priceCategory2;
  private DefinedUnit definedUnit1;
  private DefinedUnit definedUnit2;
  private DefinedUnit definedUnit3;
  private DefinedUnit definedUnit4;

  @Before
  public void setUp() {
    client =
        new Client.Builder().name("Klaus").surname("Klauser").email("klaus@klausur.at").build();
    clientRepository.save(client);

    location =
        new Location.Builder()
            .name("Location 1")
            .place("Place 1")
            .postalCode("1110")
            .street("Street 1")
            .country("Austria")
            .build();
    locationRepository.save(location);

    hall =
        new Hall.Builder()
            .name("Hall 1")
            .location(location)
            .boundaryPoint(new Point(10, 20))
            .version(1)
            .build();
    hallRepository.save(hall);

    artist = new Artist.Builder().name("Bob").surname("Dylan").build();
    artistRepository.save(artist);

    event =
        new Event.Builder()
            .name("Event 1")
            .duration(Duration.ofMinutes(90L))
            .content("Some description")
            .category(EventCategory.CONCERT)
            .hall(hall)
            .artists(Collections.singletonList(artist))
            .build();
    eventRepository.save(event);

    unit1 =
        new Unit.Builder()
            .name("Reihe 1, Platz 8")
            .lowerBoundary(new Point(0, 7))
            .upperBoundary(new Point(0, 7))
            .capacity(1)
            .hall(hall)
            .build();
    unitRepository.save(unit1);

    unit2 =
        new Unit.Builder()
            .name("Sektor Hinten")
            .lowerBoundary(new Point(0, 9))
            .upperBoundary(new Point(9, 19))
            .capacity(3)
            .hall(hall)
            .build();
    unitRepository.save(unit2);

    performance1 =
        new Performance.Builder()
            .name("Performance 1")
            .event(event)
            .startAt(LocalDateTime.now().plusDays(3))
            .build();
    performanceRepository.save(performance1);

    performance2 =
        new Performance.Builder()
            .name("Performance 2")
            .event(event)
            .startAt(LocalDateTime.now().plusDays(4))
            .build();
    performanceRepository.save(performance2);

    priceCategory1 =
        new PriceCategory.Builder()
            .name("Kategorie 1")
            .priceInCents(3200)
            .color(new Color(0, 0, 0))
            .event(event)
            .build();
    priceCategoryRepository.save(priceCategory1);

    priceCategory2 =
        new PriceCategory.Builder()
            .name("Kategorie 2")
            .priceInCents(1700)
            .color(new Color(0, 0, 0))
            .event(event)
            .build();
    priceCategoryRepository.save(priceCategory2);

    definedUnit1 =
        new DefinedUnit.Builder()
            .performance(performance1)
            .priceCategory(priceCategory1)
            .unit(unit1)
            .capacityFree(unit1.getCapacity())
            .build();
    definedUnitRepository.save(definedUnit1);

    definedUnit2 =
        new DefinedUnit.Builder()
            .performance(performance1)
            .priceCategory(priceCategory2)
            .unit(unit2)
            .capacityFree(unit2.getCapacity())
            .build();
    definedUnitRepository.save(definedUnit2);

    definedUnit3 =
        new DefinedUnit.Builder()
            .performance(performance2)
            .priceCategory(priceCategory1)
            .unit(unit1)
            .capacityFree(unit1.getCapacity())
            .build();
    definedUnitRepository.save(definedUnit3);

    definedUnit4 =
        new DefinedUnit.Builder()
            .performance(performance2)
            .priceCategory(priceCategory2)
            .unit(unit2)
            .capacityFree(unit2.getCapacity())
            .build();
    definedUnitRepository.save(definedUnit4);

    ticketRequestDtoList =
        Arrays.asList(
            new TicketRequestDto.Builder().definedUnitId(definedUnit1.getId()).amount(1).build(),
            new TicketRequestDto.Builder().definedUnitId(definedUnit2.getId()).amount(1).build());
    reservationRequestDto =
        new ReservationRequestDto.Builder()
            .performanceId(performance1.getId())
            .clientId(client.getId())
            .ticketRequests(ticketRequestDtoList)
            .build();
  }

  @After
  public void cleanUp() {
    ticketRepository.deleteAll();
    invoiceRepository.deleteAll();
    clientRepository.deleteAll();
    definedUnitRepository.deleteAll();
    performanceRepository.deleteAll();
    priceCategoryRepository.deleteAll();
    unitRepository.deleteAll();
    eventRepository.deleteAll();
    hallRepository.deleteAll();
    locationRepository.deleteAll();
    artistRepository.deleteAll();
  }

  @Test
  public void whenRequestReservationNullClientId_thenStatus400() {
    reservationRequestDto.setClientId(null);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullPerformanceId_thenStatus400() {
    reservationRequestDto.setPerformanceId(null);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullTickets_thenStatus400() {
    reservationRequestDto.setTicketRequests(null);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationEmptyTickets_thenStatus400() {
    reservationRequestDto.setTicketRequests(Collections.emptyList());

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullTicketId_thenStatus400() {
    reservationRequestDto.getTicketRequests().get(0).setDefinedUnitId(null);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationInvalidAmount_thenStatus400() {
    reservationRequestDto.getTicketRequests().get(0).setAmount(0);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationInvalidClientId_thenStatus404() {
    reservationRequestDto.setClientId(-1L);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void whenRequestReservationInvalidPerformanceId_thenStatus404() {
    reservationRequestDto.setPerformanceId(-1L);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void whenRequestReservationInvalidDefinedUnitId_thenStatus404() {
    ticketRequestDtoList.get(0).setDefinedUnitId(definedUnit3.getId());

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void whenRequestReservationPastPerformance_thenStatus400() {
    performance1.setStartAt(LocalDateTime.now().minusMinutes(14));
    performanceRepository.save(performance1);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationCapacityTooHigh_thenStatus400() {
    ticketRequestDtoList.get(0).setAmount(unit1.getCapacity() + 1);

    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservation_thenCorrectInvoiceReturned() {
    int requestedTicketCount = 0;
    for (TicketRequestDto ticketRequestDto : ticketRequestDtoList) {
      requestedTicketCount += ticketRequestDto.getAmount();
    }
    Response response = post(RESERVATION_ENDPOINT, reservationRequestDto);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());

    InvoiceDto invoiceDto = response.as(InvoiceDto.class);
    assertThat(invoiceDto.getTickets().size()).isEqualTo(requestedTicketCount);
    assertThat(invoiceDto.getClientId()).isEqualTo(reservationRequestDto.getClientId());
    assertThat(invoiceDto.getReservationCode()).isNotBlank();
    assertThat(invoiceDto.getId()).isNotNull();
    assertThat(invoiceDto.isCancelled()).isFalse();
    assertThat(invoiceDto.isPaid()).isFalse();
  }

  private Response post(String endpoint, Object body) {
    return RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(reservationRequestDto)
            .when()
            .post(RESERVATION_ENDPOINT)
            .then()
            .extract()
            .response();
  }
}
