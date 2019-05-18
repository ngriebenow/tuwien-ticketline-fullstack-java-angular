package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
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
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
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
public class InvoiceServiceTest {

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

  @Autowired private InvoiceService invoiceService;

  @MockBean private InvoiceRepository mockInvoiceRepository;
  @MockBean private PerformanceRepository mockPerformanceRepository;
  @MockBean private ClientRepository mockClientRepository;
  @MockBean private DefinedUnitRepository mockDefinedUnitRepository;

  @Before
  public void setUp() {
    performanceOne = new Performance.Builder().id(INVOICE_PERFORMANCE_ID).build();

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
  public void givenNothing_whenGetById_thenNotFoundException() {
    when(mockInvoiceRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThatThrownBy(() -> invoiceService.getOneById(INVOICE_ID))
        .isInstanceOf(NotFoundException.class);

    verify(mockInvoiceRepository).findById(INVOICE_ID);
  }

  @Test
  public void whenGetByNullId_thenNotFoundException() {
    when(mockInvoiceRepository.findById(null)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> invoiceService.getOneById(null)).isInstanceOf(NotFoundException.class);

    verify(mockInvoiceRepository).findById(null);
  }

  @Test
  public void givenOneInvoice_whenGetById_thenInvoiceDtoReturned() {
    when(mockInvoiceRepository.findById(INVOICE_ID)).thenReturn(Optional.of(invoiceOne));

    InvoiceDto invoiceDto = invoiceService.getOneById(INVOICE_ID);
    assertThat(invoiceDto).isEqualTo(invoiceDtoOne);
    assertThat(invoiceDto.getTickets()).isEqualTo(invoiceDtoOne.getTickets());

    verify(mockInvoiceRepository).findById(INVOICE_ID);
  }

  // TODO: what about validating for create invoice for performances in the past?

  @Test
  public void whenCreateInvoiceEmptyTickets_thenThrowException() {
    reservationRequestDto.setTicketRequests(Collections.emptyList());

    assertThatThrownBy(() -> invoiceService.createInvoice(reservationRequestDto, false))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void whenCreateInvoiceNullTickets_thenThrowException() {
    reservationRequestDto.setTicketRequests(null);

    assertThatThrownBy(() -> invoiceService.createInvoice(reservationRequestDto, false))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void whenCreateInvoiceAmountZero_thenThrowException() {
    ticketRequestDto.setAmount(0);

    assertThatThrownBy(() -> invoiceService.createInvoice(reservationRequestDto, false))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void whenCreateInvoiceNegativeAmount_thenThrowException() {
    ticketRequestDto.setAmount(-1);

    assertThatThrownBy(() -> invoiceService.createInvoice(reservationRequestDto, false))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void whenCreateInvoiceInvalidClientId_thenThrowException() {
    Long invalidClientId = 32L;
    reservationRequestDto.setClientId(invalidClientId);

    when(mockPerformanceRepository.findById(any(Long.class)))
        .thenReturn(Optional.of(performanceOne));
    when(mockClientRepository.findById(invalidClientId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> invoiceService.createInvoice(reservationRequestDto, false))
        .isInstanceOf(NotFoundException.class);

    verify(mockPerformanceRepository).findById(any(Long.class));
    verify(mockClientRepository).findById(invalidClientId);
  }

  @Test
  public void whenCreateInvoiceInvalidPerformanceId_thenThrowException() {
    Long invalidPerformanceId = 32L;
    reservationRequestDto.setPerformanceId(invalidPerformanceId);

    when(mockPerformanceRepository.findById(invalidPerformanceId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> invoiceService.createInvoice(reservationRequestDto, false))
        .isInstanceOf(NotFoundException.class);

    verify(mockPerformanceRepository).findById(invalidPerformanceId);
  }

  @Test
  public void whenCreateInvoiceInvalidDefinedUnit_thenThrowException() {
    ticketRequestDto.setDefinedUnitId(TICKET_DEFINED_UNIT_ID + 73);

    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));
    when(mockClientRepository.findById(INVOICE_CLIENT_ID)).thenReturn(Optional.of(clientOne));
    List<Long> definedUnitIdList =
        reservationRequestDto.getTicketRequests().stream()
            .map(TicketRequestDto::getDefinedUnitId)
            .collect(Collectors.toList());
    when(mockDefinedUnitRepository.findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList))
        .thenReturn(new ArrayList<>());

    assertThatThrownBy(() -> invoiceService.createInvoice(reservationRequestDto, false))
        .isInstanceOf(NotFoundException.class);

    verify(mockDefinedUnitRepository).findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList);
    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
    verify(mockClientRepository).findById(INVOICE_CLIENT_ID);
  }

  @Test
  public void whenCreateInvoiceCapacityOverflow_thenThrowException() {
    ticketRequestDto.setAmount(TICKET_DEFINED_UNIT_CAPACITY + 1);

    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));
    when(mockClientRepository.findById(INVOICE_CLIENT_ID)).thenReturn(Optional.of(clientOne));
    List<Long> definedUnitIdList =
        reservationRequestDto.getTicketRequests().stream()
            .map(TicketRequestDto::getDefinedUnitId)
            .collect(Collectors.toList());
    when(mockDefinedUnitRepository.findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList))
        .thenReturn(Collections.singletonList(definedUnitOne));

    assertThatThrownBy(() -> invoiceService.createInvoice(reservationRequestDto, false))
        .isInstanceOf(ValidationException.class);

    verify(mockDefinedUnitRepository)
        .findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList);
    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
    verify(mockClientRepository).findById(INVOICE_CLIENT_ID);
  }

  // @Test
  public void whenCreateValidInvoice_thenInvoiceCreated() {
    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));
    when(mockClientRepository.findById(INVOICE_CLIENT_ID)).thenReturn(Optional.of(clientOne));
    List<Long> definedUnitIdList =
        reservationRequestDto.getTicketRequests().stream()
            .map(TicketRequestDto::getDefinedUnitId)
            .collect(Collectors.toList());
    when(mockDefinedUnitRepository.findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList))
        .thenReturn(Collections.singletonList(definedUnitOne));

    // TODO: ids are not being set here because of the underlying mock objects
    InvoiceDto invoiceDto = invoiceService.createInvoice(reservationRequestDto, false);
    assertThat(invoiceDto).isEqualTo(invoiceDtoOne);
    assertThat(invoiceDto.getTickets()).isEqualTo(invoiceDtoOne.getTickets());

    verify(mockDefinedUnitRepository)
        .findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList);
    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
    verify(mockClientRepository).findById(INVOICE_CLIENT_ID);
  }
}
