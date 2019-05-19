package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
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
@RequestMapping("/invoices")
@Api("invoices")
public class InvoiceEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceEndpoint.class);

  private InvoiceService invoiceService;

  @Autowired
  public InvoiceEndpoint(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  /** Buy tickets for the specified performance. */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(
      value = "Buy tickets for the specified performance",
      authorizations = {@Authorization("apiKey")})
  public InvoiceDto buy(@RequestBody ReservationRequestDto reservationRequestDto) {
    LOGGER.info(
        "Attempting to buy tickets for performance {}", reservationRequestDto.getPerformanceId());
    return invoiceService.buyTickets(reservationRequestDto);
  }
}
