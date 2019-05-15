package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.time.Duration;
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
  public EventDto get(@PathVariable Long id) throws NotFoundException {
    return eventService.getOneById(id);
  }

  /** Javadoc. */
  @RequestMapping(value = "/{id}/performances", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get performances by event id",
      authorizations = {@Authorization(value = "apiKey")})
  public List<PerformanceDto> get(
      @PathVariable Long id, @RequestParam Integer page, @RequestParam Integer count)
      throws NotFoundException {

    Pageable p = PageRequest.of(page, count);
    return eventService.getPerformancesOfEvent(id, p);
  }

  /** Javadoc. */
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get filtered events",
      authorizations = {@Authorization(value = "apiKey")})
  public List<EventDto> get(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String content,
      @RequestParam(required = false) Integer duration,
      @RequestParam(required = false) EventCategory eventCategory,
      @RequestParam(required = false) String artistName,
      @RequestParam(required = false) Integer priceInCents,
      @RequestParam(required = false) String hallName,
      @RequestParam(required = false) Long hallId,
      @RequestParam(required = false) Long locationId,
      @RequestParam(required = false) String locationName,
      @RequestParam(required = false) String locationCountry,
      @RequestParam(required = false) String locationStreet,
      @RequestParam(required = false) String locationPlace,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "1000") Integer count) {

    // TODO: pageable default values
    Pageable p = PageRequest.of(page, count);

    EventFilterDto eventFilterDto =
        new EventFilterDto.Builder()
            .eventCategory(eventCategory)
            .artistName(artistName)
            .content(content)
            .name(name)
            .priceInCents(priceInCents)
            .hallId(hallId)
            .hallName(hallName)
            .locationId(locationId)
            .locationName(locationName)
            .locationCountry(locationCountry)
            .locationPlace(locationPlace)
            .locationStreet(locationStreet)
            .build();

    if (duration != null) {
      eventFilterDto.setDuration(Duration.ofMinutes(duration));
    }

    return eventService.getEventsFiltered(eventFilterDto, p);
  }
}
