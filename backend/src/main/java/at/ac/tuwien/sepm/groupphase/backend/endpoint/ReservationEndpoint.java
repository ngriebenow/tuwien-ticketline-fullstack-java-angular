package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@Api("reservations")
public class ReservationEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReservationEndpoint.class);

  private ReservationService reservationService;

  @Autowired
  public ReservationEndpoint(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  /** Reserve tickets for the specified performance. */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(
      value = "Reserve tickets for the specified performance",
      authorizations = {@Authorization("apiKey")})
  public InvoiceDto reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
    LOGGER.info(
        "Attempting to reserve tickets for performance with id {}",
        reservationRequestDto.getPerformanceId());
    return reservationService.reserveTickets(reservationRequestDto);
  }
}
