package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class ReservationEndpointTest extends BaseIntegrationTest {

  private static final String RESERVATION_ENDPOINT = "/reservations";

  private ReservationRequestDto reservationRequestDto;
  private List<TicketRequestDto> ticketRequestDtoList;

  @Before
  public void setUp() {
    ticketRequestDtoList =
        Collections.singletonList(
            new TicketRequestDto.Builder().definedUnitId(1L).amount(1).build());
    reservationRequestDto = new ReservationRequestDto.Builder()
        .performanceId(1L)
        .clientId(1L)
        .ticketRequests(ticketRequestDtoList)
        .build();
  }

  @Test
  public void whenRequestReservationNullClientId_thenStatus400() {
    reservationRequestDto.setClientId(null);
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(
                reservationRequestDto
            )
            .when()
            .post(RESERVATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullPerformanceId_thenStatus400() {
    reservationRequestDto.setPerformanceId(null);
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(
                reservationRequestDto
            )
            .when()
            .post(RESERVATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullTickets_thenStatus400() {
    reservationRequestDto.setTicketRequests(null);
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(
                reservationRequestDto
            )
            .when()
            .post(RESERVATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationEmptyTickets_thenStatus400() {
    reservationRequestDto.setTicketRequests(Collections.emptyList());
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(
                reservationRequestDto
            )
            .when()
            .post(RESERVATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationNullTicketId_thenStatus400() {
    reservationRequestDto.getTicketRequests().get(0).setDefinedUnitId(null);
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(
                reservationRequestDto
            )
            .when()
            .post(RESERVATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void whenRequestReservationInvalidAmount_thenStatus400() {
    reservationRequestDto.getTicketRequests().get(0).setAmount(0);
    Response response =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .body(
                reservationRequestDto
            )
            .when()
            .post(RESERVATION_ENDPOINT)
            .then()
            .extract()
            .response();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }
}
