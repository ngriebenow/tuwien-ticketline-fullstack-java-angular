package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.InvoiceFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.security.Principal;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  /** Get a sinlge invoice by id. */
  @GetMapping("/{id}")
  @ApiOperation(
      value = "Get an invoice by its id",
      authorizations = {@Authorization("apiKey")})
  public InvoiceDto getById(@PathVariable Long id) {
    LOGGER.info("Get invoice {}", id);
    return invoiceService.getOneById(id);
  }

  /** Add JavaDoc. */
  @GetMapping
  @ApiOperation(
      value = "Get filtered invoices",
      authorizations = {@Authorization("apiKey")})
  public List<InvoiceDto> get(
      @RequestParam(required = false) String reservationCode,
      @RequestParam(required = false) Boolean isPaid,
      @RequestParam(required = false) Boolean isCancelled,
      @RequestParam(required = false) String clientName,
      @RequestParam(required = false) String clientEmail,
      @RequestParam(required = false) String performanceName,
      @RequestParam(required = false) Long invoiceNumber,
      @RequestParam @NotNull @Min(0L) Integer page,
      @RequestParam @NotNull @Min(1L) Integer count) {
    Pageable pageable = PageRequest.of(page, count);

    InvoiceFilterDto invoiceFilterDto =
        new InvoiceFilterDto.Builder()
            .reservationCode(reservationCode)
            .isPaid(isPaid)
            .isCancelled(isCancelled)
            .clientName(clientName)
            .clientEmail(clientEmail)
            .performanceName(performanceName)
            .invoiceNumber(invoiceNumber)
            .build();

    return invoiceService.getFiltered(invoiceFilterDto, pageable);
  }

  /** Buy tickets for the specified performance. */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(
      value = "Buy tickets for the specified performance",
      authorizations = {@Authorization("apiKey")})
  public InvoiceDto buy(
      @RequestBody ReservationRequestDto reservationRequestDto, Principal principal) {
    LOGGER.info(
        "Attempting to buy tickets for performance {}", reservationRequestDto.getPerformanceId());
    return invoiceService.buyTickets(reservationRequestDto, principal.getName());
  }

  /** Reserve tickets for the specified performance. */
  @PostMapping("/reserve")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(
      value = "Reserve tickets for the specified performance",
      authorizations = {@Authorization("apiKey")})
  public InvoiceDto reserve(
      @RequestBody ReservationRequestDto reservationRequestDto, Principal principal) {
    LOGGER.info(
        "Attempting to reserve tickets for performance with id {}",
        reservationRequestDto.getPerformanceId());
    return invoiceService.reserveTickets(reservationRequestDto, principal.getName());
  }

  @PostMapping("/{id}/pay")
  @ApiOperation(
      value = "Pay tickets for an existing invoice",
      authorizations = {@Authorization("apiKey")})
  public InvoiceDto pay(
      @PathVariable Long id, @RequestBody List<Long> ticketIds, Principal principal) {
    LOGGER.info("Attempting to pay tickets {} for invoice {}", ticketIds, id);
    return invoiceService.payTickets(id, ticketIds, principal.getName());
  }

  @PostMapping("/{id}/cancel")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(
      value = "Cancel a paid reservation and return the cancelled invoice",
      authorizations = {@Authorization("apiKey")})
  public InvoiceDto cancel(@PathVariable Long id, Principal principal) {
    LOGGER.info("Attempting to cancel invoice {}", id);
    return invoiceService.cancelPaidInvoice(id, principal.getName());
  }

  @DeleteMapping("/{id}")
  @ApiOperation(
      value = "Delete a reservation",
      authorizations = {@Authorization("apiKey")})
  public void delete(@PathVariable Long id) {
    LOGGER.info("Attempting to delete reservation {}", id);
    invoiceService.deleteReservation(id);
  }
}
