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
}