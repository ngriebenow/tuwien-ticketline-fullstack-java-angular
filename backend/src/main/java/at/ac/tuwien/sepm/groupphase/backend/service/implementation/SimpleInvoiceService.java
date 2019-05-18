package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class SimpleInvoiceService implements InvoiceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleInvoiceService.class);
  private static final Random RANDOM = new SecureRandom();
  // TODO: turn this in to a global config?
  private static final int TICKET_SALT_LENGTH = 32;
  private static final int RESERVATION_CODE_LENGTH = 6;
  private static final char[] RESERVATION_CODE_CHARACTERS =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

  private final InvoiceRepository invoiceRepository;
  private PerformanceRepository performanceRepository;
  private ClientRepository clientRepository;
  private DefinedUnitRepository definedUnitRepository;
  private TicketRepository ticketRepository;

  private final InvoiceMapper invoiceMapper;

  /** Create a new InvoiceService. */
  @Autowired
  public SimpleInvoiceService(
      InvoiceRepository invoiceRepository,
      PerformanceRepository performanceRepository,
      ClientRepository clientRepository,
      DefinedUnitRepository definedUnitRepository,
      TicketRepository ticketRepository,
      InvoiceMapper invoiceMapper) {
    this.invoiceRepository = invoiceRepository;
    this.performanceRepository = performanceRepository;
    this.clientRepository = clientRepository;
    this.definedUnitRepository = definedUnitRepository;
    this.ticketRepository = ticketRepository;
    this.invoiceMapper = invoiceMapper;
  }

  @Override
  public InvoiceDto getOneById(Long id) {
    return invoiceMapper.invoiceToInvoiceDto(
        invoiceRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  String msg = "Can't find invoice with id " + id;
                  LOGGER.error(msg);
                  return new NotFoundException(msg);
                }));
  }

  @Transactional
  @Override
  public InvoiceDto createInvoice(
      @Valid ReservationRequestDto reservationRequestDto, boolean isPaid) {
    Long performanceId = reservationRequestDto.getPerformanceId();
    Long clientId = reservationRequestDto.getClientId();

    LOGGER.info("Creating invoice for performance {} for client {}", performanceId, clientId);

    Performance performance =
        performanceRepository
            .findById(performanceId)
            .orElseThrow(
                () -> {
                  String msg = "Can't find performance with id " + performanceId;
                  LOGGER.error(msg);
                  return new NotFoundException(msg);
                });
    Client client =
        clientRepository
            .findById(clientId)
            .orElseThrow(
                () -> {
                  String msg = "Can't find client with id " + clientId;
                  LOGGER.error(msg);
                  return new NotFoundException(msg);
                });

    if (performance.getStartAt().isBefore(LocalDateTime.now())) {
      String msg = "Can't reserve tickets for bygone performance " + performanceId;
      LOGGER.error(msg);
      throw new ValidationException(msg);
    }

    Invoice invoice =
        new Invoice.Builder()
            .isPaid(isPaid)
            // TODO: generate an invoice number from sequence if isPaid
            .isCancelled(false)
            .client(client)
            .reservationCode(generateReservationCode())
            .build();

    createTickets(performance, invoice, reservationRequestDto.getTicketRequests());

    invoice = invoiceRepository.save(invoice);

    return invoiceMapper.invoiceToInvoiceDto(invoice);
  }

  /**
   * Create tickets for the given definedUnitIds and attaches them to invoice. Note that this will
   * update the corresponding defined units but not save them. This will happen as soon as you
   * save the invoice or tickets.
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
          DefinedUnit definedUnit =
              requestedDefinedUnits.stream()
                  .filter(defUnit -> defUnit.getId().equals(id))
                  .findFirst()
                  .orElseThrow(
                      () ->
                          new NotFoundException(
                              "Defined unit "
                                  + id
                                  + " doesn't belong to performance "
                                  + performance.getId()));

          int newCapacity = definedUnit.getCapacityFree() - ticketRequestDto.getAmount();
          if (newCapacity < 0) {
            throw new ValidationException("Defined unit " + id + " doesn't have enough capacity.");
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
