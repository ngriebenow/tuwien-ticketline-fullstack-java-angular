package at.ac.tuwien.sepm.groupphase.backend.repository.custom;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventRanking;
import java.util.List;

public interface CustomEventRepository {

  /**
   * Get the best events by the total number of tickets sold.
   * @param limit The number how much events should be returned.
   * @param eventFilterDto The criteria which the event must be conform to. Only the category is
   *        currently considered.
   * @return The events with the most number of sold tickets.
   */
  List<EventRanking> getBest(Integer limit, EventFilterDto eventFilterDto);

}
