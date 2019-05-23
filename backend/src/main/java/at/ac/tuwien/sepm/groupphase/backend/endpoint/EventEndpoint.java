package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(EventEndpoint.class);
  private final EventService eventService;

  public EventEndpoint(EventService eventService) {
    this.eventService = eventService;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get event by id",
      authorizations = {@Authorization(value = "apiKey")})
  public EventDto getById(@PathVariable Long id) {
    LOGGER.info("getById " + id);
    return eventService.getOneById(id);
  }

  /** Return all performances which belong to a certain event. */
  @RequestMapping(value = "/{id}/performances", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get performances by event id",
      authorizations = {@Authorization(value = "apiKey")})
  public List<PerformanceSearchResultDto> getPerformancesById(
      @PathVariable Long id,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer count) {
    LOGGER.info("getPerformancesById " + id);

    Pageable p = getPageable(page, count);

    return eventService.getPerformancesFiltered(id, p);
  }

  private Pageable getPageable(Integer page, Integer count) {
    Pageable p;
    if (page != null && count != null) {
      p = PageRequest.of(page, count);
    } else {
      p = Pageable.unpaged();
    }
    return p;
  }

  /** Return the filtered events according to the specified search criteria. */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(
      value = "Get filtered events",
      authorizations = {@Authorization(value = "apiKey")})
  public List<EventSearchResultDto> get(
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
      @RequestParam(required = false) String startAtTime,
      @RequestParam(required = false) String startAtDate,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer count) {

    LocalTime time = null;
    if (startAtTime != null && !startAtTime.isEmpty()) {
      try {
        time = LocalTime.parse(startAtTime, DateTimeFormatter.ofPattern("H-mm"));
      } catch (DateTimeParseException ex) {
        throw new ValidationException("Could not parse time.");
      }

    }
    LocalDate date = null;
    if (startAtDate != null && !startAtDate.isEmpty()) {
      try {
        date = LocalDate.parse(startAtDate, DateTimeFormatter.ofPattern("d.MM.yyyy"));
      } catch (DateTimeParseException ex) {
        throw new ValidationException("Could not parse date.");
      }

    }

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
            .startAtDate(date)
            .startAtTime(time)
            .build();

    LOGGER.info("get with filter " + eventFilterDto);

    if (duration != null) {
      eventFilterDto.setDuration(Duration.ofMinutes(duration));
    }

    Pageable p = getPageable(page, count);
    return eventService.getEventsFiltered(eventFilterDto, p);
  }
}
