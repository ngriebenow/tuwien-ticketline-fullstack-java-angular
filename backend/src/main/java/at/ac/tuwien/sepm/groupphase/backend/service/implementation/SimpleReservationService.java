package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
import java.time.LocalDateTime;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class SimpleReservationService implements ReservationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReservationService.class);
  private static final Long PRE_PERFORMANCE_RESERVATION_CLEAR_MINUTES = 30L;

  private PerformanceRepository performanceRepository;

  private InvoiceService invoiceService;

  @Autowired
  public SimpleReservationService(
      PerformanceRepository performanceRepository, InvoiceService invoiceService) {
    this.performanceRepository = performanceRepository;
    this.invoiceService = invoiceService;
  }

  @Transactional
  @Override
  public InvoiceDto reserveTickets(@Valid ReservationRequestDto reservationRequestDto) {
    Long performanceId = reservationRequestDto.getPerformanceId();
    Long clientId = reservationRequestDto.getClientId();

    LOGGER.info("Reserving tickets for performance {} for client {}", performanceId, clientId);

    Performance performance =
        performanceRepository
            .findById(performanceId)
            .orElseThrow(
                () -> {
                  String msg = "Can't find performance with id " + performanceId;
                  LOGGER.error(msg);
                  return new NotFoundException(msg);
                });

    if (performance
        .getStartAt()
        .minusMinutes(PRE_PERFORMANCE_RESERVATION_CLEAR_MINUTES)
        .isBefore(LocalDateTime.now())) {
      String msg = "Can't reserve tickets for bygone performance " + performanceId;
      LOGGER.error(msg);
      throw new ValidationException(msg);
    }
    return invoiceService.createInvoice(reservationRequestDto, false);
  }
}
