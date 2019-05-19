package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventRankingDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface EventService {

  /**
   * Get the event by id.
   *
   * @param id the id of the event
   * @return the event
   * @throws NotFoundException if the id could not be found
   */
  EventDto getOneById(Long id) throws NotFoundException;

  /**
   * Get the best events according to the total tickets sold.
   *
   * @param limit the number of events which should be returned
   * @return ordered event ranking list each consisting of the event, the sold tickets and the rank
   */
  List<EventRankingDto> getBestEvents(Integer limit);

  /**
   * Get all events which satisfy the given constraints in specification.
   *
   * @param eventFilterDto the search criteria which all returned events fulfill
   * @param pageable the pageable for determing the page
   * @return the list of events
   */
  List<EventDto> getEventsFiltered(EventFilterDto eventFilterDto, Pageable pageable);

  /**
   * Get the performances of the event by its id.
   *
   * @param id the id of the event
   * @param pageable the pageable for determing the page
   * @return the list of performances which belong to the event
   */
  List<PerformanceDto> getPerformancesOfEvent(Long id, Pageable pageable);
}
