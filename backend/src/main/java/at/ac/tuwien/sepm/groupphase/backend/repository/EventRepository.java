package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import java.util.List;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository
    extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

  List<Event> findAllByNameContainsAndCategoryEqualsAndContentContains(
      String name, EventCategory eventCategory, String content,
      Specification<Event> specification, Pageable pageable);



}
