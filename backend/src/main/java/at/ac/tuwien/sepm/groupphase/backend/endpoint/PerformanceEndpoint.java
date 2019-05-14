package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performances")
@Api("performances")
public class PerformanceEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceEndpoint.class);

  // TODO: implement
  @PostMapping("/{id}/reserve")
  @ApiOperation(
      value = "Reserve tickets for the specified performance",
      authorizations = {@Authorization("apiKey")})
  public InvoiceDto reserve(
      @PathVariable Long id, @RequestBody ReservationRequestDto reservationRequestDto) {
    LOGGER.info("Attempting to reserve tickets for performance with id {}", id);
    return null;
  }
}
