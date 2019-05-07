package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import java.util.List;

public interface EventService {

  /**
   * Find all event entries.
   *
   * @return ordered list of all event entries
   */
  List<Event> findAll();


  /**
   * Find a single event entry by id.
   *
   * @param id the is of the event entry
   * @return the event entry
   */
  Event findOne(Long id);

}
