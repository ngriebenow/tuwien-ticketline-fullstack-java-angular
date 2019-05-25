package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefinedUnitRepository extends JpaRepository<DefinedUnit, Long> {

  List<DefinedUnit> findAllByPerformanceAndCapacityFreeIsGreaterThan(
      Performance performance, int capacityFree);

  /**
   * Return a list of defined units belonging to performance whose id is an element of ids.
   *
   * @param performance the Performance to find DefniedUnits for.
   * @param ids the DefinedUnit ids to search for.
   * @return the filtered list of DefinedUnits.
   */
  List<DefinedUnit> findAllByPerformanceAndIdIn(Performance performance, List<Long> ids);

  /**
   * Return a list of the defined units form this performance.
   *
   * @param performance contains only the id of the performance
   * @return the list of the defined units
   */
  List<DefinedUnit> findAllByPerformanceIsLike(Performance performance);
}
