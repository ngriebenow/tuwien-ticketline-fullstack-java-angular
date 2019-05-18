package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/halls")
@Api(value = "halls")
public class HallEndpoint {

  private final HallService hallService;

  public HallEndpoint(HallService hallService) {
    this.hallService = hallService;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get hall by id",
      authorizations = {@Authorization(value = "apiKey")})
  public HallDto get(@PathVariable Long id) {
    return hallService.getOneById(id);
  }

  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Post hall",
      authorizations = {@Authorization(value = "apiKey")})
  public HallDto post(){
    return new HallDto();
  }

  @RequestMapping(method = RequestMethod.PUT)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Put hall",
      authorizations = {@Authorization(value = "apiKey")})
  public HallDto put(HallDto hallDto){
    return new HallDto();
  }

  @RequestMapping(value = "/{id}/units", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get hall units by id",
      authorizations = {@Authorization(value = "apiKey")})
  public HallDto getUnits(@PathVariable Long id) {
    return hallService.getOneById(id);
  }
}
