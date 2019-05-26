package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/performances")
@Api(value = "performances")
public class PerformanceEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceEndpoint.class);
  private final PerformanceService performanceService;

  public PerformanceEndpoint(PerformanceService performanceService){
    this.performanceService = performanceService;
  }

  @RequestMapping(value = "/hall-viewing/defined-units/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get defined units by performance id",
      authorizations = {@Authorization(value = "apiKey")})
  public List<DefinedUnitDto> getDefinedUnitsByPerformanceId(@PathVariable Long id){
    LOGGER.info("getDefinedUnitsByPerformanceId " + id);
    Performance performance = new Performance();
    performance.setId(id);
    return performanceService.getDefinedUnitsByPerformanceId(performance);
  }
}
