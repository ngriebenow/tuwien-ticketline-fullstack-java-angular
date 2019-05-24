package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ClientDto;
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
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class InvoiceEndpointTest extends BaseIntegrationTest {

  private static final String INVOICE_ENDPOINT = "/invoices";
  private static final String RESERVATION_ENDPOINT = INVOICE_ENDPOINT + "/reserve";

  private ReservationRequestDto reservationRequestDtoOne;
  private ReservationRequestDto reservationRequestDtoTwo;
  private List<TicketRequestDto> ticketRequestDtoListOne;
  private List<TicketRequestDto> ticketRequestDtoListTwo;

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

  private Client clientOne;
  private Client clientTwo;
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
    clientOne =
        new Client.Builder().name("Klaus").surname("Klauser").email("klaus@klausur.at").build();
    clientRepository.save(clientOne);

    clientTwo =
        new Client.Builder()
            .name("Rabarbara")
            .surname("Arabrabar")
            .email("rabarbara@arabrabar.at")
            .build();
    clientRepository.save(clientTwo);

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
            .name("Freuden der Freizeit")
            .duration(Duration.ofMinutes(90L))
            .content("Alle wollen mal ausspannen und neues ausprobieren. Bist du dabei?")
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
            .name("Sonntagskrunch")
            .event(event)
            .startAt(LocalDateTime.now().plusDays(3))
            .build();
    performanceRepository.save(performance1);

    performance2 =
        new Performance.Builder()
            .name("Luftballonkochkurz")
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

    ticketRequestDtoListOne =
        Arrays.asList(
            new TicketRequestDto.Builder().definedUnitId(definedUnit1.getId()).amount(1).build(),
            new TicketRequestDto.Builder().definedUnitId(definedUnit2.getId()).amount(1).build());
    reservationRequestDtoOne =
        new ReservationRequestDto.Builder()
            .performanceId(performance1.getId())
            .clientId(clientOne.getId())
            .ticketRequests(ticketRequestDtoListOne)
            .build();

    ticketRequestDtoListTwo =
        Arrays.asList(
            new TicketRequestDto.Builder().definedUnitId(definedUnit3.getId()).amount(1).build(),
            new TicketRequestDto.Builder().definedUnitId(definedUnit4.getId()).amount(1).build());
    reservationRequestDtoTwo =
        new ReservationRequestDto.Builder()
            .performanceId(performance2.getId())
            .clientId(clientTwo.getId())
            .ticketRequests(ticketRequestDtoListTwo)
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
    reservationRequestDtoOne.setClientId(null);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullPerformanceId_thenStatus400() {
    reservationRequestDtoOne.setPerformanceId(null);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullTickets_thenStatus400() {
    reservationRequestDtoOne.setTicketRequests(null);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationEmptyTickets_thenStatus400() {
    reservationRequestDtoOne.setTicketRequests(Collections.emptyList());

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullTicketId_thenStatus400() {
    reservationRequestDtoOne.getTicketRequests().get(0).setDefinedUnitId(null);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationInvalidAmount_thenStatus400() {
    reservationRequestDtoOne.getTicketRequests().get(0).setAmount(0);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationInvalidClientId_thenStatus404() {
    reservationRequestDtoOne.setClientId(-1L);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void whenRequestReservationInvalidPerformanceId_thenStatus404() {
    reservationRequestDtoOne.setPerformanceId(-1L);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void whenRequestReservationInvalidDefinedUnitId_thenStatus404() {
    ticketRequestDtoListOne.get(0).setDefinedUnitId(definedUnit3.getId());

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void whenRequestReservationPastPerformance_thenStatus400() {
    performance1.setStartAt(LocalDateTime.now().minusMinutes(14));
    performanceRepository.save(performance1);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationCapacityTooHigh_thenStatus400() {
    ticketRequestDtoListOne.get(0).setAmount(unit1.getCapacity() + 1);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void givenReservedDefinedUnit_whenReserveAgain_thenStatus400() {
    postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservation_thenCorrectInvoiceReturned() {
    int requestedTicketCount = 0;
    for (TicketRequestDto ticketRequestDto : ticketRequestDtoListOne) {
      requestedTicketCount += ticketRequestDto.getAmount();
    }
    Response response = postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());

    InvoiceDto invoiceDto = response.as(InvoiceDto.class);

    assertThat(invoiceDto.getTickets().size()).isEqualTo(requestedTicketCount);
    assertThat(invoiceDto.getClient().getId()).isEqualTo(reservationRequestDtoOne.getClientId());
    assertThat(invoiceDto.getReservationCode()).isNotBlank();
    assertThat(invoiceDto.getId()).isNotNull();
    assertThat(invoiceDto.isCancelled()).isFalse();
    assertThat(invoiceDto.isPaid()).isFalse();
  }

  @Test
  public void givenTwoInvoices_whenFilter_thenTwoInvoicesReturned() {
    postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);
    postResponse(RESERVATION_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(2);
  }

  @Test
  public void givenUnpaidInvoices_whenFilterInvoicesIsPaid_thenEmptyListReturned() {
    postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);
    postResponse(RESERVATION_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("isPaid", "true");
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.isEmpty()).isTrue();
  }

  @Test
  public void givenUnpaidInvoices_whenFilterInvoicesIsNotPaid_thenInvoicesReturned() {
    postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne);
    postResponse(RESERVATION_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("isPaid", "false");
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(2);
  }

  @Test
  public void givenPaidInvoices_whenFilterInvoicesIsPaid_thenInvoicesReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("isPaid", "true");
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(2);
  }

  @Test
  public void givenPaidInvoices_whenFilterInvoicesIsNotPaid_thenEmptyListReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("isPaid", "false");
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(0L);
  }

  @Test
  public void givenTwoInvoices_whenFilterInvoiceNumberOne_thenInvoiceOneReturned() {
    Long invoiceNumberOne =
        postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne)
            .body()
            .as(InvoiceDto.class)
            .getNumber();
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("invoiceNumber", invoiceNumberOne.toString());
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().response().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(1L);
    assertThat(invoiceDtoPage.get(0).getNumber()).isEqualTo(invoiceNumberOne);
  }

  @Test
  public void givenTwoInvoices_whenFilterReservationCodeOne_thenInvoiceOneReturned() {
    String reservationCodeOne =
        postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne)
            .body()
            .as(InvoiceDto.class)
            .getReservationCode();
    postResponse(RESERVATION_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("reservationCode", reservationCodeOne);
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().response().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(1L);
    assertThat(invoiceDtoPage.get(0).getReservationCode()).isEqualTo(reservationCodeOne);
  }

  @Test
  public void givenTwoInvoices_whenFilterInvalidReservationCode_thenNothingReturned() {
    String reservationCodeOne =
        postResponse(RESERVATION_ENDPOINT, reservationRequestDtoOne)
            .body()
            .as(InvoiceDto.class)
            .getReservationCode();
    String reservationCodeTwo =
        postResponse(RESERVATION_ENDPOINT, reservationRequestDtoTwo)
            .body()
            .as(InvoiceDto.class)
            .getReservationCode();

    Map<String, String> params = new HashMap<>();
    params.put("reservationCode", reservationCodeOne + reservationCodeTwo);
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.isEmpty()).isTrue();
  }

  @Test
  public void givenTowInvoices_whenFilterInvoicesClientOneName_thenThatInvoiceReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("clientName", clientOne.getName());
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(1);

    ClientDto client = invoiceDtoPage.get(0).getClient();
    assertThat(client.getId()).isEqualTo(clientOne.getId());
  }

  @Test
  public void givenTowInvoices_whenFilterInvoicesClientOneSurName_thenThatInvoiceReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("clientName", clientOne.getSurname());
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(1);

    ClientDto client = invoiceDtoPage.get(0).getClient();
    assertThat(client.getId()).isEqualTo(clientOne.getId());
  }

  @Test
  public void givenTowInvoices_whenFilterInvoicesClientOneFullName_thenThatInvoiceReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("clientName", clientOne.getName() + " " + clientOne.getSurname());
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(1);

    ClientDto client = invoiceDtoPage.get(0).getClient();
    assertThat(client.getId()).isEqualTo(clientOne.getId());
  }

  @Test
  public void givenTowInvoices_whenFilterInvoicesClientOneEmail_thenThatInvoiceReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("clientEmail", clientOne.getEmail());
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(1);

    ClientDto client = invoiceDtoPage.get(0).getClient();
    assertThat(client.getId()).isEqualTo(clientOne.getId());
  }

  @Test
  public void givenTowInvoices_whenFilterInvoicesPerformanceOneName_thenThatInvoiceReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("performanceName", performance1.getName());
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(1);
    assertThat(invoiceDtoPage.get(0).getTickets().get(0).getPerformanceId())
        .isEqualTo(performance1.getId());
  }

  @Test
  public void givenTowInvoices_whenFilterInvoicesEventName_thenBothReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("performanceName", event.getName());
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(2);
  }

  @Test
  public void givenTowInvoices_whenFilterInvalidPerformanceName_thenNothingReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("performanceName", performance1.getName() + performance2.getName());
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(0);
  }

  @Test
  public void givenTowInvoices_whenFilterEmptyPerformanceName_thenNothingReturned() {
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoOne);
    postResponse(INVOICE_ENDPOINT, reservationRequestDtoTwo);

    Map<String, String> params = new HashMap<>();
    params.put("performanceName", "");
    params.put("page", "0");
    params.put("count", "20");

    ValidatableResponse response = get(INVOICE_ENDPOINT, params);

    assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());

    List<InvoiceDto> invoiceDtoPage = listFromResponse(response, InvoiceDto.class);

    assertThat(invoiceDtoPage.size()).isEqualTo(2);
  }

  private Response getResponse(String endpoint, Map<String, String> parameters) {
    return get(endpoint, parameters).extract().response();
  }

  private ValidatableResponse get(String endpoint, Map<String, String> parameters) {
    RequestSpecification specification =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix);
    parameters.forEach(specification::queryParam);
    return specification.when().get(endpoint).then();
  }

  private Response postResponse(String endpoint, Object body) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
        .body(body)
        .when()
        .post(endpoint)
        .then()
        .extract()
        .response();
  }

  private <T> List<T> listFromResponse(ValidatableResponse response, Class<T> clazz) {
    return response.extract().jsonPath().getList(".", clazz);
  }
}
