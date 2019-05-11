package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/events")
@Api(value = "events")
public class EventEndpoint {

  private final EventService eventService;

  public EventEndpoint(EventService eventService) {
    this.eventService = eventService;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get event by id",
      authorizations = {@Authorization(value = "apiKey")})
  public EventDto get(@PathVariable Long id) {
    return eventService.getOneById(id);
  }

  /** Javadoc. */
  @RequestMapping(value = "/{id}/performances", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get performances by event id",
      authorizations = {@Authorization(value = "apiKey")})
  public List<PerformanceDto> get(
      @PathVariable Long id, @RequestParam Integer page, @RequestParam Integer count) {

    Pageable p = PageRequest.of(page, count);
    return eventService.getPerformancesOfEvent(id, p);
  }

  /** Javadoc. */
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get filtered events",
      authorizations = {@Authorization(value = "apiKey")})
  public List<PerformanceDto> get(
      @RequestParam String name, @RequestParam Integer page, @RequestParam Integer count) {

    Pageable p = PageRequest.of(page, count);

    return null;
  }
}
