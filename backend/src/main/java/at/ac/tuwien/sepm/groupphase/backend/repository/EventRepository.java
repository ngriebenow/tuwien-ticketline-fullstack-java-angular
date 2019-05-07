package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

  /**
   * Find a single event entry by id.
   *
   * @param id the is of the event entry
   * @return Optional containing the event entry
   */
  Optional<Event> findOneById(Long id);


  /**
   * Find all event entries ordered by published at name (ascending).
   *
   * @return ordered list of all event entries
   */
  List<Event> findAllByOrderByNameAsc();

}
