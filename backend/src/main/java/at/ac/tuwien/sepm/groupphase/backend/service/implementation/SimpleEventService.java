package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventRankingDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventRanking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event_;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventRankingMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventSearchResultMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceSearchResultMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.pricecategory.PriceCategoryMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.specification.EventSpecification;
import at.ac.tuwien.sepm.groupphase.backend.specification.PerformanceSpecification;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleEventService implements EventService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventService.class);
  @Autowired private EventRepository eventRepository;
  @Autowired private PerformanceRepository performanceRepository;
  @Autowired private PriceCategoryRepository priceCategoryRepository;

  @Autowired private EventMapper eventMapper;
  @Autowired private PerformanceSearchResultMapper performanceSearchResultMapper;
  @Autowired private EventSearchResultMapper eventSearchResultMapper;
  @Autowired private PriceCategoryMapper priceCategoryMapper;
  @Autowired private EventRankingMapper eventRankingMapper;

  @PersistenceContext
  EntityManager entityManager;

  @Transactional(readOnly = true)
  @Override
  public EventDto getOneById(Long id) throws NotFoundException {
    LOGGER.info("getOneById " + id);

    Event event = eventRepository.findById(id).orElseThrow(NotFoundException::new);
    EventDto eventDto = eventMapper.eventToEventDto(event);

    eventDto.setPriceCategories(
        priceCategoryRepository.findAllByEventOrderByPriceInCentsAsc(event).stream()
            .map(pc -> priceCategoryMapper.priceCategoryToPriceCategoryDto(pc))
            .collect(Collectors.toList()));

    return eventDto;
  }

  @Transactional(readOnly = true)
  @Override
  public List<EventRankingDto> getBest(Integer limit, EventFilterDto eventFilterDto) {
    LOGGER.info("getBest with filter " + eventFilterDto);

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<EventRanking> events = cb.createQuery(EventRanking.class);
    Root<Ticket> nr = events.from(Ticket.class);

    Path<Event> path = nr.get("definedUnit").get("performance").get("event");

    Predicate checkForCategory = cb.and();
    if (eventFilterDto.getEventCategory() != null) {
      LOGGER.info("Add event category filter with " + eventFilterDto.getEventCategory());
      checkForCategory = cb.equal(
          path.get(Event_.category),eventFilterDto.getEventCategory());
    }

    events.multiselect(cb.count(nr), path.get("id"), path.get("name"));
    events.groupBy(path.get("id"));
    events.where(cb.and(
        cb.equal(nr.get("isCancelled"),false),
        checkForCategory));
    events.orderBy(cb.desc(cb.count(nr)));

    TypedQuery<EventRanking> tq = entityManager.createQuery(events).setMaxResults(limit);

    List<EventRanking> evs = tq.getResultList();

    return evs.stream().map(
        e -> eventRankingMapper.eventRankingToEventRankingDto(e)).collect(
        Collectors.toList());
  }

  @Transactional(readOnly = true)
  @Override
  public List<EventSearchResultDto> getFiltered(
      EventFilterDto eventFilterDto, Pageable pageable) {
    LOGGER.info("getFiltered " + eventFilterDto);

    Specification<Event> specification = EventSpecification.getEventSpecification(eventFilterDto);
    specification = specification.and(EventSpecification.likeHallLocation(eventFilterDto));
    specification = specification.and(EventSpecification.likeArtist(eventFilterDto));
    specification = specification.and(EventSpecification.likePrice(eventFilterDto));

    Page<Event> events = eventRepository.findAll(specification, pageable);

    List<EventSearchResultDto> eventDtos = new ArrayList<>();

    for (Event e : events) {
      LOGGER.info("Found event " + e);
      EventSearchResultDto eventDto = eventSearchResultMapper.eventToEventSearchResultDto(e);
      List<PriceCategory> priceCategories =
          priceCategoryRepository.findAllByEventOrderByPriceInCentsAsc(e);
      eventDto.setPriceRange(formatPriceRange(priceCategories));

      List<PerformanceSearchResultDto> performanceSearchResultDtos =
          getPerformancesFiltered(e.getId(), eventFilterDto, Pageable.unpaged());

      if (performanceSearchResultDtos.size() > 0) {
        LOGGER.info("Performances found. Add event to event list.");
        eventDtos.add(eventDto);
        eventDto.setPerformances(performanceSearchResultDtos);
      }
    }

    return eventDtos;
  }

  private String formatPriceRange(List<PriceCategory> priceCategories) {
    String price;
    if (priceCategories.size() > 0) {
      price = String.format("%.0f", priceCategories.get(0).getPriceInCents() / 100.);

      if (priceCategories.size() > 1) {
        price +=  " - " + String.format(
                    "%.0f",
                    priceCategories.get(priceCategories.size() - 1).getPriceInCents() / 100.);
      }
      price += " €";
    } else {
      price = "kein Preis";
    }

    return price;
  }

  private List<PerformanceSearchResultDto> getPerformancesFiltered(
      Long id, EventFilterDto eventFilterDto, Pageable pageable) throws NotFoundException {
    LOGGER.info("getPerformancesFiltered with id " + id + " and filter " + eventFilterDto);

    Specification<Performance> specification = PerformanceSpecification.like(id, eventFilterDto);

    return performanceRepository
        .findAll(specification, pageable)
        .stream().map(
        p -> performanceSearchResultMapper.performanceToPerformanceSearchResultDto(p))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  @Override
  public List<PerformanceSearchResultDto> getPerformancesByEventId(Long id, Pageable pageable)
      throws NotFoundException {
    LOGGER.info("getPerformancesOfEvent " + id);
    return getPerformancesFiltered(id,new EventFilterDto(),pageable);
  }
}
