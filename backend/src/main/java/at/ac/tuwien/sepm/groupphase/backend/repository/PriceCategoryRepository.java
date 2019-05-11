package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Long> {
  List<PriceCategory> findAllByEvent(Event event);
}
