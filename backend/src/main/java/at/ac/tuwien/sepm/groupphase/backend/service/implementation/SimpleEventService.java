package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventRankingDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.artist.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.specification.UserSpecification;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SimpleEventService implements EventService {

  @Autowired private EventRepository eventRepository;

  @Autowired private PerformanceRepository performanceRepository;

  @Autowired private EventMapper eventMapper;

  @Autowired private PerformanceMapper performanceMapper;

  @Autowired private ArtistMapper artistMapper;

  /*public SimpleEventService(EventRepository eventRepository, EventMapper eventMapper) {
    this.eventRepository = eventRepository;
    this.eventMapper = eventMapper;
  }*/

  @Override
  public EventDto getOneById(Long id) throws NotFoundException {
    return eventMapper.eventToEventDto(
        eventRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  @Override
  public List<EventRankingDto> getBestEvents(Integer limit) {
    return null;
  }

  @Override
  public List<EventDto> getEventsFiltered(EventFilterDto eventFilterDto, Pageable pageable) {

    /*
    Specification<Event> specification = UserSpecification
        .contains("name",eventFilterDto.getName());
    specification = specification.and(
        UserSpecification.endures("duration",eventFilterDto.getDuration(), Duration.ofMinutes(30))
    );
    specification = specification.and(
        UserSpecification.contains("content",eventFilterDto.getName())
    );
    specification = specification.and(
        UserSpecification.contains("content",eventFilterDto.getName())
    );
    specification = specification.and(
        UserSpecification.belongsTo("eventCategory",eventFilterDto.getEventCategory())
    );*/

    Specification<Event> specification = likeHallLocation(eventFilterDto);


    List<Event> events = eventRepository.findAllByNameContainsAndCategoryEqualsAndContentContains(
        eventFilterDto.getName(),
        eventFilterDto.getEventCategory(),
        eventFilterDto.getContent(),
        specification,
        pageable);


    List<EventDto> eventDtos = new ArrayList<>();
    events.forEach(e -> eventDtos.add(eventMapper.eventToEventDto(e)));
    return eventDtos;
  }

  @Override
  public List<PerformanceDto> getPerformancesOfEvent(Long id, Pageable pageable)
      throws NotFoundException {

    Event event = eventRepository.findById(id).orElseThrow(NotFoundException::new);
    List<PerformanceDto> performanceDtos = new ArrayList<>();

    performanceRepository.findAllByEvent(event,pageable)
        .forEach(p -> performanceDtos.add(performanceMapper.performanceToPerformanceDto(p)));

    return performanceDtos;
  }


  /** Javadoc. */
  public static Specification<Event> likeHallLocation(EventFilterDto eventFilterDto) {
    return new Specification<Event>() {
      @Override
      public Predicate toPredicate(
          Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate hallName = criteriaBuilder.equal(root.get("hall").get("name"),
            eventFilterDto.getHallName());
        Predicate hallId = criteriaBuilder.equal(root.get("hall").get("id"),
            eventFilterDto.getHallId());
        Predicate locationId = criteriaBuilder.equal(root.get("hall").get("location").get("id"),
            eventFilterDto.getLocationId());
        Predicate locationName = criteriaBuilder.equal(root.get("hall").get("location").get("name"),
            eventFilterDto.getLocationId());
        Predicate locationPlace = criteriaBuilder.equal(root.get("hall").get("location").get("place"),
            eventFilterDto.getLocationId());
        Predicate locationCountry = criteriaBuilder.equal(root.get("hall").get("location").get("country"),
            eventFilterDto.getLocationId());
        Predicate locationStreet = criteriaBuilder.equal(root.get("hall").get("location").get("street"),
            eventFilterDto.getLocationId());
        Predicate locationPostalCode = criteriaBuilder.equal(root.get("hall").get("location").get("postalCode"),
            eventFilterDto.getLocationId());

        return criteriaBuilder.and(hallName,hallId,locationId,locationName,
            locationPlace,locationCountry,locationStreet,locationPostalCode);
      }
    };
  }
}
