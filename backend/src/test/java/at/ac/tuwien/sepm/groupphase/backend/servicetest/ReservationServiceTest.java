package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "unit-test")
public class ReservationServiceTest {

  private static final Long INVOICE_ID = 1L;
  private static final Long INVOICE_CLIENT_ID = 1L;
  private static final Long INVOICE_PERFORMANCE_ID = 1L;
  private static final boolean INVOICE_IS_PAID = false;
  private static final boolean INVOICE_IS_CANCELED = false;
  private static final String INVOICE_RESERVATION_CODE = "25434";

  private static final Long TICKET_ID = 1L;
  private static final Long TICKET_DEFINED_UNIT_ID = 1L;
  private static final int TICKET_DEFINED_UNIT_CAPACITY = 3;
  private static final String TICKET_SALT = "1mN0tr4nd0M";
  private static final int TICKET_PRICE = 1560;
  private static final boolean TICKET_IS_CANCELED = INVOICE_IS_CANCELED;

  private Invoice invoiceOne;
  private Ticket ticketOne;
  private Performance performanceOne;
  private DefinedUnit definedUnitOne;
  private PriceCategory priceCategoryOne;
  private Client clientOne;

  private InvoiceDto invoiceDtoOne;
  private TicketDto ticketDtoOne;
  private ReservationRequestDto reservationRequestDto;
  private TicketRequestDto ticketRequestDto;

  @Autowired private ReservationService reservationService;

  @MockBean private InvoiceService mockInvoiceService;
  @MockBean private PerformanceRepository mockPerformanceRepository;

  @Before
  public void setUp() {
    performanceOne =
        new Performance.Builder()
            .id(INVOICE_PERFORMANCE_ID)
            .startAt(LocalDateTime.now().plusDays(7))
            .build();

    priceCategoryOne = new PriceCategory.Builder().priceInCents(TICKET_PRICE).build();

    definedUnitOne =
        new DefinedUnit.Builder()
            .id(TICKET_DEFINED_UNIT_ID)
            .capacityFree(TICKET_DEFINED_UNIT_CAPACITY)
            .performance(performanceOne)
            .priceCategory(priceCategoryOne)
            .build();

    ticketOne =
        new Ticket.Builder()
            .id(TICKET_ID)
            .salt(TICKET_SALT)
            .definedUnit(definedUnitOne)
            .isCancelled(TICKET_IS_CANCELED)
            .build();

    clientOne = new Client.Builder().id(INVOICE_CLIENT_ID).build();

    invoiceOne =
        new Invoice.Builder()
            .id(INVOICE_ID)
            .isPaid(INVOICE_IS_PAID)
            .isCancelled(INVOICE_IS_CANCELED)
            .reservationCode(INVOICE_RESERVATION_CODE)
            .tickets(new ArrayList<>())
            .client(clientOne)
            .build();
    invoiceOne.addTicket(ticketOne);

    ticketDtoOne =
        new TicketDto.Builder()
            .id(TICKET_ID)
            .priceInCents(TICKET_PRICE)
            .invoiceId(INVOICE_ID)
            .performanceId(INVOICE_PERFORMANCE_ID)
            .build();

    invoiceDtoOne =
        new InvoiceDto.Builder()
            .id(INVOICE_ID)
            .clientId(INVOICE_CLIENT_ID)
            .reservationCode(INVOICE_RESERVATION_CODE)
            .isPaid(INVOICE_IS_PAID)
            .isCancelled(INVOICE_IS_CANCELED)
            .tickets(Collections.singletonList(ticketDtoOne))
            .build();

    ticketRequestDto =
        new TicketRequestDto.Builder().definedUnitId(TICKET_DEFINED_UNIT_ID).amount(1).build();

    reservationRequestDto =
        new ReservationRequestDto.Builder()
            .clientId(INVOICE_CLIENT_ID)
            .performanceId(INVOICE_PERFORMANCE_ID)
            .ticketRequests(Collections.singletonList(ticketRequestDto))
            .build();
  }

  @Test
  public void whenReserveInvalidPerformanceId_thenThrowsException() {
    Long invalidPerformanceId = 33L;
    reservationRequestDto.setPerformanceId(invalidPerformanceId);
    when(mockPerformanceRepository.findById(invalidPerformanceId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> reservationService.reserveTickets(reservationRequestDto))
        .isInstanceOf(NotFoundException.class);

    verify(mockPerformanceRepository).findById(invalidPerformanceId);
  }

  @Test
  public void whenReserveInReservationClearWindow_thenThrowsException() {
    performanceOne.setStartAt(LocalDateTime.now().minusMinutes(22));

    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));

    assertThatThrownBy(() -> reservationService.reserveTickets(reservationRequestDto))
        .isInstanceOf(ValidationException.class);

    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
  }
}
