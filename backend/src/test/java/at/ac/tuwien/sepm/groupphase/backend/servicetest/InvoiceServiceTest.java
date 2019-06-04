package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ClientDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import java.time.ZonedDateTime;
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

  private static final Long CLIENT_ID = 1L;
  private static final String CLIENT_NAME = "Friedrich";
  private static final String CLIENT_SURNAME = "Schabernackl";
  private static final String CLIENT_EMAIL = "witzhaus@nac.kl";

  private static final String USER_NAME = "user";
  private static final boolean USER_ENABLED = true;
  private static final int USER_FAILED_COUNTER = 0;
  private static final String USER_PASSWORD = "password";
  private static final String USER_AUTHORITY = "ROLE_USER";

  private Invoice invoiceOne;
  private Ticket ticketOne;
  private Performance performanceOne;
  private DefinedUnit definedUnitOne;
  private PriceCategory priceCategoryOne;
  private Client clientOne;
  private User userOne;

  private InvoiceDto invoiceDtoOne;
  private TicketDto ticketDtoOne;
  private ReservationRequestDto reservationRequestDto;
  private TicketRequestDto ticketRequestDto;
  private ClientDto clientDtoOne;
  private UserDto userDtoOne;

  @Autowired private InvoiceService invoiceService;

  @MockBean private InvoiceRepository mockInvoiceRepository;
  @MockBean private PerformanceRepository mockPerformanceRepository;
  @MockBean private ClientRepository mockClientRepository;
  @MockBean private UserRepository mockUserRepository;
  @MockBean private DefinedUnitRepository mockDefinedUnitRepository;

  @Before
  public void setUp() {
    performanceOne =
        new Performance.Builder()
            .id(INVOICE_PERFORMANCE_ID)
            .startAt(ZonedDateTime.now().plusDays(7))
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

    clientOne =
        new Client.Builder()
            .id(CLIENT_ID)
            .name(CLIENT_NAME)
            .surname(CLIENT_SURNAME)
            .email(CLIENT_EMAIL)
            .build();

    userOne = new User();
    userOne.setUsername(USER_NAME);
    userOne.setEnabled(USER_ENABLED);
    userOne.setFailedLoginCounter(USER_FAILED_COUNTER);
    userOne.setPassword(USER_PASSWORD);
    userOne.setAuthority(USER_AUTHORITY);

    invoiceOne =
        new Invoice.Builder()
            .id(INVOICE_ID)
            .isPaid(INVOICE_IS_PAID)
            .isCancelled(INVOICE_IS_CANCELED)
            .reservationCode(INVOICE_RESERVATION_CODE)
            .tickets(new ArrayList<>())
            .client(clientOne)
            .soldBy(userOne)
            .build();
    invoiceOne.addTicket(ticketOne);

    ticketDtoOne =
        new TicketDto.Builder()
            .id(TICKET_ID)
            .priceInCents(TICKET_PRICE)
            .startAt(performanceOne.getStartAt())
            .definedUnitId(TICKET_DEFINED_UNIT_ID)
            .performanceId(INVOICE_PERFORMANCE_ID)
            .build();

    clientDtoOne =
        new ClientDto.Builder()
            .id(CLIENT_ID)
            .name(CLIENT_NAME)
            .surname(CLIENT_SURNAME)
            .email(CLIENT_EMAIL)
            .build();

    userDtoOne = new UserDto();
    userDtoOne.setUsername(USER_NAME);
    userDtoOne.setEnabled(String.valueOf(USER_ENABLED));
    userDtoOne.setFailedLoginCounter(USER_FAILED_COUNTER);
    userDtoOne.setPassword(USER_PASSWORD);

    invoiceDtoOne =
        new InvoiceDto.Builder()
            .id(INVOICE_ID)
            .client(clientDtoOne)
            .soldBy(userDtoOne)
            .reservationCode(INVOICE_RESERVATION_CODE)
            .isPaid(INVOICE_IS_PAID)
            .isCancelled(INVOICE_IS_CANCELED)
            .tickets(Collections.singletonList(ticketDtoOne))
            .build();

    ticketRequestDto =
        new TicketRequestDto.Builder().definedUnitId(TICKET_DEFINED_UNIT_ID).amount(1).build();

    reservationRequestDto =
        new ReservationRequestDto.Builder()
            .clientId(CLIENT_ID)
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

  @Test
  public void whenBuyTicketsForPastPerformance_thenThrowException() {
    performanceOne.setStartAt(ZonedDateTime.now().minusDays(7));
    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));
    when(mockClientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientOne));
    when(mockUserRepository.findById(USER_NAME)).thenReturn(Optional.of(userOne));

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(ValidationException.class);

    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
    verify(mockClientRepository).findById(CLIENT_ID);
    verify(mockUserRepository).findById(USER_NAME);
  }

  @Test
  public void whenBuyTicketsEmptyTickets_thenThrowException() {
    reservationRequestDto.setTicketRequests(Collections.emptyList());

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void whenBuyTicketsNullTickets_thenThrowException() {
    reservationRequestDto.setTicketRequests(null);

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void whenBuyTicketsAmountZero_thenThrowException() {
    ticketRequestDto.setAmount(0);

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void whenBuyTicketsNegativeAmount_thenThrowException() {
    ticketRequestDto.setAmount(-1);

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void whenBuyTicketsInvalidClientId_thenThrowException() {
    Long invalidClientId = 32L;
    reservationRequestDto.setClientId(invalidClientId);

    when(mockPerformanceRepository.findById(any(Long.class)))
        .thenReturn(Optional.of(performanceOne));

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(NotFoundException.class);

    verify(mockClientRepository).findById(invalidClientId);
  }

  @Test
  public void whenBuyTicketsInvalidPerformanceId_thenThrowException() {
    Long invalidPerformanceId = 32L;
    reservationRequestDto.setPerformanceId(invalidPerformanceId);

    when(mockClientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientOne));
    when(mockPerformanceRepository.findById(invalidPerformanceId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(NotFoundException.class);

    verify(mockClientRepository).findById(CLIENT_ID);
    verify(mockPerformanceRepository).findById(invalidPerformanceId);
  }

  @Test
  public void whenBuyTicketsInvalidDefinedUnit_thenThrowException() {
    ticketRequestDto.setDefinedUnitId(TICKET_DEFINED_UNIT_ID + 73);

    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));
    when(mockClientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientOne));
    when(mockUserRepository.findById(USER_NAME)).thenReturn(Optional.of(userOne));
    List<Long> definedUnitIdList =
        reservationRequestDto.getTicketRequests().stream()
            .map(TicketRequestDto::getDefinedUnitId)
            .collect(Collectors.toList());
    when(mockDefinedUnitRepository.findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList))
        .thenReturn(new ArrayList<>());

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(NotFoundException.class);

    verify(mockDefinedUnitRepository)
        .findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList);
    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
    verify(mockClientRepository).findById(CLIENT_ID);
    verify(mockUserRepository).findById(USER_NAME);
  }

  @Test
  public void whenBuyTicketsCapacityOverflow_thenThrowException() {
    ticketRequestDto.setAmount(TICKET_DEFINED_UNIT_CAPACITY + 1);

    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));
    when(mockClientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientOne));
    when(mockUserRepository.findById(USER_NAME)).thenReturn(Optional.of(userOne));
    List<Long> definedUnitIdList =
        reservationRequestDto.getTicketRequests().stream()
            .map(TicketRequestDto::getDefinedUnitId)
            .collect(Collectors.toList());
    when(mockDefinedUnitRepository.findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList))
        .thenReturn(Collections.singletonList(definedUnitOne));

    assertThatThrownBy(() -> invoiceService.buyTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(ValidationException.class);

    verify(mockDefinedUnitRepository)
        .findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList);
    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
    verify(mockClientRepository).findById(CLIENT_ID);
    verify(mockUserRepository).findById(USER_NAME);
  }

  @Test
  public void whenCreateValidInvoice_thenInvoiceCreated() {
    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));
    when(mockClientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientOne));
    when(mockUserRepository.findById(USER_NAME)).thenReturn(Optional.of(userOne));
    List<Long> definedUnitIdList =
        reservationRequestDto.getTicketRequests().stream()
            .map(TicketRequestDto::getDefinedUnitId)
            .collect(Collectors.toList());
    when(mockDefinedUnitRepository.findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList))
        .thenReturn(Collections.singletonList(definedUnitOne));
    doReturn(invoiceOne).when(mockInvoiceRepository).save(any(Invoice.class));

    InvoiceDto invoiceDto = invoiceService.buyTickets(reservationRequestDto, USER_NAME);
    assertThat(invoiceDto).isEqualTo(invoiceDtoOne);
    assertThat(invoiceDto.getTickets()).isEqualTo(invoiceDtoOne.getTickets());

    verify(mockInvoiceRepository).save(any(Invoice.class));
    verify(mockDefinedUnitRepository)
        .findAllByPerformanceAndIdIn(performanceOne, definedUnitIdList);
    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
    verify(mockClientRepository).findById(CLIENT_ID);
    verify(mockUserRepository).findById(USER_NAME);
  }

  @Test
  public void whenReserveInvalidPerformanceId_thenThrowsException() {
    Long invalidPerformanceId = 33L;
    reservationRequestDto.setPerformanceId(invalidPerformanceId);

    when(mockClientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientOne));
    when(mockPerformanceRepository.findById(invalidPerformanceId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> invoiceService.reserveTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(NotFoundException.class);

    verify(mockClientRepository).findById(CLIENT_ID);
    verify(mockPerformanceRepository).findById(invalidPerformanceId);
  }

  @Test
  public void whenReserveInReservationClearWindow_thenThrowsException() {
    performanceOne.setStartAt(ZonedDateTime.now().minusMinutes(22));

    when(mockClientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientOne));
    when(mockPerformanceRepository.findById(INVOICE_PERFORMANCE_ID))
        .thenReturn(Optional.of(performanceOne));
    when(mockUserRepository.findById(USER_NAME)).thenReturn(Optional.of(userOne));

    assertThatThrownBy(() -> invoiceService.reserveTickets(reservationRequestDto, USER_NAME))
        .isInstanceOf(ValidationException.class);

    verify(mockClientRepository).findById(CLIENT_ID);
    verify(mockPerformanceRepository).findById(INVOICE_PERFORMANCE_ID);
    verify(mockUserRepository).findById(USER_NAME);
  }
}
