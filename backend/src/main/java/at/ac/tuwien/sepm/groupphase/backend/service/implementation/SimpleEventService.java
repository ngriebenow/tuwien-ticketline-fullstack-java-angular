package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventRankingDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.artist.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.specification.EventSpecification;
import at.ac.tuwien.sepm.groupphase.backend.specification.UserSpecification;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    Specification<Event> specification = EventSpecification.getEventSpecification(eventFilterDto);
    specification = specification.and(EventSpecification.likeHallLocation(eventFilterDto));
    Page<Event> events = eventRepository.findAll(specification, pageable);

    List<EventDto> eventDtos = new ArrayList<>();
    events.forEach(e -> eventDtos.add(eventMapper.eventToEventDto(e)));
    return eventDtos;
  }

  @Override
  public List<PerformanceDto> getPerformancesOfEvent(Long id, Pageable pageable)
      throws NotFoundException {

    Event event = eventRepository.findById(id).orElseThrow(NotFoundException::new);
    List<PerformanceDto> performanceDtos = new ArrayList<>();

    performanceRepository
        .findAllByEvent(event, pageable)
        .forEach(p -> performanceDtos.add(performanceMapper.performanceToPerformanceDto(p)));

    return performanceDtos;
  }
}
