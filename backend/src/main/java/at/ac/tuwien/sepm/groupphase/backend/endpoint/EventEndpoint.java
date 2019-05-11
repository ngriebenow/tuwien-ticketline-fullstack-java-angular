package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
      @RequestParam String name,
      @RequestParam EventCategory eventCategory,
      @RequestParam String artistName,
      @RequestParam Integer priceInCents,
      @RequestParam String hallName,
      @RequestParam Long hallId,
      @RequestParam Long locationId,
      @RequestParam String locationName,
      @RequestParam String locationCountry,
      @RequestParam String locationStreet,
      @RequestParam String locationPlace,
      @RequestParam Integer page,
      @RequestParam Integer count) {

    Pageable p = PageRequest.of(page, count);

    EventFilterDto eventFilterDto = new EventFilterDto.Builder()
        .eventCategory(eventCategory)
        .artistName(artistName)
        .content(content)
        .duration(duration)
        .name(name)
        .priceInCents(priceInCents)
        .hallId(hallId)
        .hallName(hallName)
        .locationId(locationId)
        .locationName(locationName)
        .locationCountry(locationCountry)
        .locationPlace(locationPlace)
        .locationStreet(locationStreet).build();

    eventService.getEventsFiltered(eventFilterDto,p);

    return null;
  }


  @ApiModelProperty(required = false, name = "The name which the event should contain")
  private String name;

  @ApiModelProperty(required = false, name = "The category which the event should be")
  private EventCategory eventCategory;

  @ApiModelProperty(required = false, name = "The artist which should feature the event")
  private ArtistDto artist;

  @ApiModelProperty(required = false, name = "The price which one ticket should cost")
  private Integer priceInCents;

  @ApiModelProperty(required = false, name = "The hall in which the event should take place")
  private HallDto hall;

  @ApiModelProperty(required = false, name = "The content description which the event should have")
  private String content;

  @ApiModelProperty(required = false, name = "The duration how long the event should take")
  private Duration duration;
}
