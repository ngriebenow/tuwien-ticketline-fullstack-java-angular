package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.InvoiceFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.InternalServerError;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.util.InvoiceNumberSequenceGenerator;
import at.ac.tuwien.sepm.groupphase.backend.specification.InvoiceSpecification;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class SimpleInvoiceService implements InvoiceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleInvoiceService.class);
  private static final Random RANDOM = new SecureRandom();

  private static final Long PRE_PERFORMANCE_RESERVATION_CLEAR_MINUTES = 30L;
  private static final int TICKET_SALT_LENGTH = 32;
  private static final int RESERVATION_CODE_LENGTH = 6;
  private static final char[] RESERVATION_CODE_CHARACTERS =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

  private final InvoiceRepository invoiceRepository;
  private PerformanceRepository performanceRepository;
  private ClientRepository clientRepository;
  private UserRepository userRepository;
  private DefinedUnitRepository definedUnitRepository;
  private TicketRepository ticketRepository;
  private InvoiceNumberSequenceGenerator invoiceNumberSequenceGenerator;

  private final InvoiceMapper invoiceMapper;

  /** Create a new InvoiceService. */
  @Autowired
  public SimpleInvoiceService(
      InvoiceRepository invoiceRepository,
      PerformanceRepository performanceRepository,
      ClientRepository clientRepository,
      UserRepository userRepository,
      DefinedUnitRepository definedUnitRepository,
      TicketRepository ticketRepository,
      InvoiceNumberSequenceGenerator invoiceNumberSequenceGenerator,
      InvoiceMapper invoiceMapper) {
    this.invoiceRepository = invoiceRepository;
    this.performanceRepository = performanceRepository;
    this.clientRepository = clientRepository;
    this.userRepository = userRepository;
    this.definedUnitRepository = definedUnitRepository;
    this.ticketRepository = ticketRepository;
    this.invoiceNumberSequenceGenerator = invoiceNumberSequenceGenerator;
    this.invoiceMapper = invoiceMapper;
  }

  @Transactional(readOnly = true)
  @Override
  public InvoiceDto getOneById(Long id) {
    LOGGER.info("Get invoice {}", id);
    String errorMessage = "Can't find invoice " + id;
    return invoiceMapper.invoiceToInvoiceDto(
        getOrThrowNotFound(invoiceRepository.findById(id), errorMessage));
  }

  @Transactional(readOnly = true)
  @Override
  public List<InvoiceDto> getFiltered(InvoiceFilterDto invoiceFilterDto, Pageable pageable) {
    LOGGER.info("Filtering for invoices");
    return invoiceRepository
        .findAll(InvoiceSpecification.buildFilterSpecification(invoiceFilterDto), pageable)
        .map(invoiceMapper::invoiceToInvoiceDto)
        .getContent();
  }

  @Transactional
  @Override
  public InvoiceDto buyTickets(
      @Valid ReservationRequestDto reservationRequestDto, String userName) {
    Long performanceId = reservationRequestDto.getPerformanceId();
    Long clientId = reservationRequestDto.getClientId();

    LOGGER.info("Buying tickets for performance {} and client {}", performanceId, clientId);

    String errorMessage = "Can't find client with id " + clientId;
    Client client = getOrThrowNotFound(clientRepository.findById(clientId), errorMessage);

    errorMessage = "Can't find performance with id " + performanceId;
    Performance performance =
        getOrThrowNotFound(performanceRepository.findById(performanceId), errorMessage);

    errorMessage = "Can't find user with name " + userName;
    User soldBy = getOrThrowNotFound(userRepository.findById(userName), errorMessage);

    if (performance.getStartAt().isBefore(LocalDateTime.now())) {
      errorMessage = "Can't buy tickets for bygone performance " + performanceId;
      throw invalid(errorMessage);
    }

    return createInvoice(
        performance, client, soldBy, reservationRequestDto.getTicketRequests(), true);
  }

  @Transactional
  @Override
  public InvoiceDto reserveTickets(
      @Valid ReservationRequestDto reservationRequestDto, String userName) {
    Long performanceId = reservationRequestDto.getPerformanceId();
    Long clientId = reservationRequestDto.getClientId();

    LOGGER.info("Reserving tickets for performance {} for client {}", performanceId, clientId);

    String errorMessage = "Can't find client with id " + clientId;
    Client client = getOrThrowNotFound(clientRepository.findById(clientId), errorMessage);

    errorMessage = "Can't find performance with id " + performanceId;
    Performance performance =
        getOrThrowNotFound(performanceRepository.findById(performanceId), errorMessage);

    errorMessage = "Can't find user with name " + userName;
    User soldBy = getOrThrowNotFound(userRepository.findById(userName), errorMessage);

    if (performance
        .getStartAt()
        .minusMinutes(PRE_PERFORMANCE_RESERVATION_CLEAR_MINUTES)
        .isBefore(LocalDateTime.now())) {
      errorMessage = "Can't reserve tickets for bygone performance " + performanceId;
      throw invalid(errorMessage);
    }

    return createInvoice(
        performance, client, soldBy, reservationRequestDto.getTicketRequests(), false);
  }

  @Transactional
  @Override
  public InvoiceDto payTickets(Long id, @NotEmpty List<@NotNull Long> ticketIds, String userName) {
    LOGGER.info("Paying tickets {} for invoice {}", ticketIds, id);

    String errorMessage = "Can't find invoice " + id;
    Invoice invoice = getOrThrowNotFound(invoiceRepository.findById(id), errorMessage);

    errorMessage = "Can't find user with name " + userName;
    User soldBy = getOrThrowNotFound(userRepository.findById(userName), errorMessage);

    if (invoice.isPaid()) {
      String msg = "Invoice " + id + " is already paid";
      throw invalid(msg);
    }

    if (invoice.isCancelled()) {
      String msg = "Invoice " + id + " is canceled";
      throw invalid(msg);
    }

    List<Long> invoiceTicketIds =
        invoice.getTickets().stream().map(Ticket::getId).collect(Collectors.toList());
    ticketIds.forEach(
        ticketId -> {
          if (!invoiceTicketIds.contains(ticketId)) {
            String msg = "Ticket " + ticketId + " is was not issued for invoice " + id;
            throw notFound(msg);
          }
        });

    List<Ticket> unboughtTickets =
        invoice.getTickets().stream()
            .filter(tic -> !ticketIds.contains(tic.getId()))
            .collect(Collectors.toList());
    deleteTickets(unboughtTickets);

    markPaid(invoice, soldBy);
    invoiceRepository.save(invoice);

    return invoiceMapper.invoiceToInvoiceDto(invoice);
  }

  @Transactional
  @Override
  public InvoiceDto cancelPaidInvoice(@NotNull Long id, String userName) {
    LOGGER.info("Cancelling invoice {}", id);

    String errorMessage = "Can't find paid and uncancelled invoice " + id;
    Invoice invoice =
        getOrThrowNotFound(
            invoiceRepository.findByIdAndIsPaidAndIsCancelled(id, true, false), errorMessage);

    if (invoiceRepository.existsByParentInvoice(invoice)) {
      errorMessage = "Invoice " + id + " has already been cancelled";
      throw invalid(errorMessage);
    }

    errorMessage = "Can't find user with name " + userName;
    User cancelledBy = getOrThrowNotFound(userRepository.findById(userName), errorMessage);

    List<Ticket> tickets = invoice.getTickets();
    tickets.forEach(tic -> tic.setCancelled(true));
    releaseDefinedUnits(tickets);

    Invoice cancelledInvoice =
        new Invoice.Builder()
            .isPaid(invoice.isPaid())
            .isCancelled(true)
            .reservationCode(invoice.getReservationCode())
            .number(invoiceNumberSequenceGenerator.getNext())
            .paidAt(LocalDate.now())
            .client(invoice.getClient())
            .soldBy(cancelledBy)
            .parentInvoice(invoice)
            .build();

    tickets.forEach(
        tic ->
            cancelledInvoice.addTicket(
                new Ticket.Builder()
                    .salt(tic.getSalt())
                    .isCancelled(true)
                    .definedUnit(tic.getDefinedUnit())
                    .build()));

    invoiceRepository.save(invoice);
    invoiceRepository.save(cancelledInvoice);

    return invoiceMapper.invoiceToInvoiceDto(cancelledInvoice);
  }

  @Transactional
  @Override
  public void deleteReservation(@NotNull Long id) {
    LOGGER.info("Deleting reservation {}", id);
    String errorMessage = "Can't find reservation with id " + id;
    Invoice invoice =
        getOrThrowNotFound(invoiceRepository.findByIdAndIsPaid(id, false), errorMessage);
    deleteTickets(invoice.getTickets());
    invoiceRepository.delete(invoice);
  }

  @Transactional
  @Override
  public BitMatrix generateQrCode(long id) {
    Invoice i;
    try {
      i = invoiceRepository.findById(id).orElseThrow();
    } catch (Exception e) {
      throw new NotFoundException("Could not find invoice with id " + id);
    }
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    byte[] tmp = ArrayUtils.addAll(("" + id).getBytes(), ";".getBytes());
    tmp = ArrayUtils.addAll(tmp, i.getPaidAt().toString().getBytes());
    BitMatrix bitMatrix;
    try {
      bitMatrix =
          qrCodeWriter.encode(
              Base64.getEncoder().encodeToString(tmp), BarcodeFormat.QR_CODE, 350, 350);
    } catch (WriterException e) {
      throw new InternalServerError();
    }
    return bitMatrix;
  }

  /**
   * Create a new invoice with the passed details. The only validation performed is if the requested
   * tickets do belong to the specified performance.
   */
  private InvoiceDto createInvoice(
      Performance performance,
      Client client,
      User soldBy,
      List<TicketRequestDto> ticketRequestDtos,
      boolean isPaid) {

    LOGGER.info(
        "Creating invoice for performance {} for client {}", performance.getId(), client.getId());

    Invoice invoice =
        new Invoice.Builder()
            .isPaid(false)
            .isCancelled(false)
            .client(client)
            .soldBy(soldBy)
            .reservationCode(generateReservationCode())
            .build();

    if (isPaid) {
      markPaid(invoice, soldBy);
    }

    createTickets(performance, invoice, ticketRequestDtos);

    invoice = invoiceRepository.save(invoice);

    return invoiceMapper.invoiceToInvoiceDto(invoice);
  }

  /**
   * Create tickets for the given definedUnitIds and attaches them to invoice. Note that this will
   * update the corresponding defined units but not save them. This will happen as soon as you save
   * the invoice or tickets.
   *
   * @param performance the performance to create tickets for.
   * @param invoice the invoice created for these tickets.
   * @param ticketRequestDtos the list of definedUnitIds with the amount of tickets to create for
   *     each.
   * @return a list of the generated tickets.
   */
  private void createTickets(
      Performance performance, Invoice invoice, List<TicketRequestDto> ticketRequestDtos) {

    List<Long> requestedDefinedUnitIds =
        ticketRequestDtos.stream()
            .map(TicketRequestDto::getDefinedUnitId)
            .distinct()
            .collect(Collectors.toList());

    List<DefinedUnit> requestedDefinedUnits =
        definedUnitRepository.findAllByPerformanceAndIdIn(performance, requestedDefinedUnitIds);

    ticketRequestDtos.forEach(
        ticketRequestDto -> {
          Long id = ticketRequestDto.getDefinedUnitId();

          String errorMessage =
              "Defined unit " + id + " doesn't belong to performance " + performance.getId();
          DefinedUnit definedUnit =
              getOrThrowNotFound(
                  requestedDefinedUnits.stream()
                      .filter(defUnit -> defUnit.getId().equals(id))
                      .findFirst(),
                  errorMessage);

          int newCapacity = definedUnit.getCapacityFree() - ticketRequestDto.getAmount();
          if (newCapacity < 0) {
            errorMessage = "Defined unit " + id + " doesn't have enough capacity";
            throw invalid(errorMessage);
          }
          definedUnit.setCapacityFree(newCapacity);

          IntStream.range(0, ticketRequestDto.getAmount())
              .forEach(
                  i ->
                      invoice.addTicket(
                          new Ticket.Builder()
                              .isCancelled(invoice.isCancelled())
                              .definedUnit(definedUnit)
                              .salt(generateTicketSalt())
                              .build()));
        });
  }

  /** Mark the invoice as paid by setting all the needed attributes. */
  private Invoice markPaid(Invoice invoice, User soldBy) {
    invoice.setPaid(true);
    invoice.setNumber(invoiceNumberSequenceGenerator.getNext());
    invoice.setPaidAt(LocalDate.now());
    invoice.setSoldBy(soldBy);
    return invoice;
  }

  /** Remove tickets from their invoice, delete them and release their defined units. */
  private void deleteTickets(List<Ticket> tickets) {
    releaseDefinedUnits(tickets);
    new ArrayList<>(tickets).forEach(tic -> tic.getInvoice().removeTicket(tic));
    ticketRepository.deleteAll(tickets);
  }

  /** Release the definedUnits referenced by the given tickets by increasing their capacity. */
  private void releaseDefinedUnits(List<Ticket> tickets) {
    tickets.forEach(
        tic -> {
          DefinedUnit definedUnit = tic.getDefinedUnit();
          definedUnit.setCapacityFree(definedUnit.getCapacityFree() + 1);
          definedUnitRepository.save(definedUnit);
        });
  }

  /** Return an object of type T or log an error and throw a NotFoundException. */
  private <T> T getOrThrowNotFound(Optional<T> optional, String msg) {
    return optional.orElseThrow(() -> notFound(msg));
  }

  /** Log an error message and return a not found exception. */
  private NotFoundException notFound(String msg) {
    LOGGER.error(msg);
    throw new NotFoundException(msg);
  }

  /** Log an error message and return a not validation exception. */
  private ValidationException invalid(String msg) {
    LOGGER.error(msg);
    throw new ValidationException(msg);
  }

  private static String generateReservationCode() {
    String reservationCode = "";
    for (int i = 0; i < RESERVATION_CODE_LENGTH; i++) {
      int idx = RANDOM.nextInt(RESERVATION_CODE_CHARACTERS.length);
      reservationCode += RESERVATION_CODE_CHARACTERS[idx];
    }
    return reservationCode;
  }

  private static String generateTicketSalt() {
    byte[] saltBytes = new byte[TICKET_SALT_LENGTH];
    RANDOM.nextBytes(saltBytes);
    return Base64.getEncoder().encodeToString(saltBytes);
  }
}
