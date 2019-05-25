package at.ac.tuwien.sepm.groupphase.backend.repository.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventRanking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event_;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.custom.CustomEventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.implementation.SimpleEventService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepositoryImpl implements CustomEventRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventService.class);

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<EventRanking> getBest(Integer limit, EventFilterDto eventFilterDto) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<EventRanking> events = cb.createQuery(EventRanking.class);
    Root<Ticket> nr = events.from(Ticket.class);

    Path<Event> path = nr.get("definedUnit").get("performance").get("event");

    Predicate checkForCategory = cb.and();
    if (eventFilterDto.getEventCategory() != null) {
      LOGGER.info("Add event category filter with " + eventFilterDto.getEventCategory());
      checkForCategory = cb.equal(
          path.get(Event_.category),eventFilterDto.getEventCategory());
    }

    events.multiselect(cb.count(nr), path.get("id"), path.get("name"));
    events.groupBy(path.get("id"));
    events.where(cb.and(
        cb.equal(nr.get("isCancelled"),false),
        checkForCategory));
    events.orderBy(cb.desc(cb.count(nr)));

    TypedQuery<EventRanking> tq = entityManager.createQuery(events).setMaxResults(limit);

    return tq.getResultList();
  }
}
