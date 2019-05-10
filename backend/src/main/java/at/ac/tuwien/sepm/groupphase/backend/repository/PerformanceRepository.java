package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface PerformanceRepository
        extends JpaRepository<Performance, Long>,
        JpaSpecificationExecutor<Performance> {

  /**
   * Find all performances which belong to the event.
   *
   * @return ordered list of all performances
   */
  List<Performance> findAllByEvent(Event event, Pageable pageable);
}
