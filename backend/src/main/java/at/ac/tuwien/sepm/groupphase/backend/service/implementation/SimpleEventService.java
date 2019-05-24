package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventRankingDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
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
  public List<EventRankingDto> getBestEvents(Integer limit) {
    return null;
  }

  @Transactional(readOnly = true)
  @Override
  public List<EventSearchResultDto> getEventsFiltered(
      EventFilterDto eventFilterDto, Pageable pageable) {
    LOGGER.info("getEventsFiltered " + eventFilterDto);

    Specification<Event> specification = EventSpecification.getEventSpecification(eventFilterDto);
    specification = specification.and(EventSpecification.likeHallLocation(eventFilterDto));
    specification = specification.and(EventSpecification.likeArtist(eventFilterDto));
    specification = specification.and(EventSpecification.likePrice(eventFilterDto));
    Page<Event> events = eventRepository.findAll(specification, pageable);

    List<EventSearchResultDto> eventDtos = new ArrayList<>();

    for (Event e : events) {
      EventSearchResultDto eventDto = eventSearchResultMapper.eventToEventSearchResultDto(e);
      List<PriceCategory> priceCategories =
          priceCategoryRepository.findAllByEventOrderByPriceInCentsAsc(e);
      eventDto.setPriceRange(formatPriceRange(priceCategories));

      List<PerformanceSearchResultDto> performanceSearchResultDtos =
          getPerformancesFiltered(e.getId(), eventFilterDto);

      if (performanceSearchResultDtos.size() > 0) {
        eventDtos.add(eventDto);
        eventDto.setPerformances(getPerformancesFiltered(e.getId(), eventFilterDto));
      }
    }

    return eventDtos;
  }

  private String formatPriceRange(List<PriceCategory> priceCategories) {
    String price = "";

    if (priceCategories.size() > 0) {
      price = String.format("%.0f", priceCategories.get(0).getPriceInCents() / 100.);

      if (priceCategories.size() > 1) {
        price +=
            " - "
                + String.format(
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
      Long id, EventFilterDto eventFilterDto) throws NotFoundException {
    LOGGER.info("getPerformancesFiltered " + id);

    List<PerformanceSearchResultDto> performanceDtos = new ArrayList<>();

    Specification<Performance> specification = PerformanceSpecification.like(id, eventFilterDto);

    performanceRepository
        .findAll(specification)
        .forEach(
            p ->
                performanceDtos.add(
                    performanceSearchResultMapper.performanceToPerformanceSearchResultDto(p)));

    return performanceDtos;
  }

  @Transactional(readOnly = true)
  @Override
  public List<PerformanceSearchResultDto> getPerformancesFiltered(Long id, Pageable pageable)
      throws NotFoundException {
    LOGGER.info("getPerformancesOfEvent " + id);

    Event event = eventRepository.findById(id).orElseThrow(NotFoundException::new);
    List<PerformanceSearchResultDto> performanceDtos = new ArrayList<>();

    performanceRepository
        .findAllByEvent(event, pageable)
        .forEach(
            p ->
                performanceDtos.add(
                    performanceSearchResultMapper.performanceToPerformanceSearchResultDto(p)));

    return performanceDtos;
  }
}
