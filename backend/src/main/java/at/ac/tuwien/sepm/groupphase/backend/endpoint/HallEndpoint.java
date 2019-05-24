package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UnitDto;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/halls")
@Api(value = "halls")
public class HallEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(HallEndpoint.class);
  private final HallService hallService;

  public HallEndpoint(HallService hallService) {
    this.hallService = hallService;
  }

  /**
   * Get the hall by id.
   *
   * @param id the id of the hall
   * @return the hall
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get hall by id",
      authorizations = {@Authorization(value = "apiKey")})
  public HallDto get(@PathVariable Long id) {
    LOGGER.info("Get hall with id " + id);
    return hallService.getOneById(id);
  }

  /**
   * Save the hall.
   *
   * @return the hall that has been saved
   */
  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Post hall",
      authorizations = {@Authorization(value = "apiKey")})
  public HallDto post(@RequestBody HallRequestDto hallRequestDto) {
    LOGGER.info("Save hall");
    return hallService.create(hallRequestDto);
  }

  /**
   * Update the hall.
   *
   * @param hallRequestDto the alerted hall
   * @return the altered hall that has been saved
   */
  @RequestMapping(method = RequestMethod.PUT)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Put hall",
      authorizations = {@Authorization(value = "apiKey")})
  public HallDto put(@RequestBody HallRequestDto hallRequestDto) {
    LOGGER.info("Update hall with id " + hallRequestDto.getId());
    return hallService.update(hallRequestDto);
  }

  /**
   * Get all units in hall by hall id.
   *
   * @param id the id of the hall
   * @return the list of units
   */
  @RequestMapping(value = "/{id}/units", method = RequestMethod.GET)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Get hall units by id",
      authorizations = {@Authorization(value = "apiKey")})
  public List<UnitDto> getUnits(@PathVariable Long id) {
    LOGGER.info("Get units of hall with id " + id);
    return hallService.getUnitsByHallId(id);
  }
}
