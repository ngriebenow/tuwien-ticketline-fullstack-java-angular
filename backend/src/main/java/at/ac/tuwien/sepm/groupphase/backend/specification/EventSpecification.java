package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {

  /** Javadoc. */
  public static Specification<Event> likeHallLocation(EventFilterDto eventFilterDto) {
    return new Specification<Event>() {
      @Override
      public Predicate toPredicate(
          Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> expressions = new ArrayList<>();

        if (eventFilterDto.getHallName() != null) {
          Predicate hallName =
              criteriaBuilder.like(
                  root.get("hall").get("name"), "%" + eventFilterDto.getHallName() + "%");
          expressions.add(hallName);
        }
        if (eventFilterDto.getHallId() != null) {
          Predicate hallId =
              criteriaBuilder.equal(root.get("hall").get("id"), eventFilterDto.getHallId());
          expressions.add(hallId);
        }
        if (eventFilterDto.getLocationId() != null) {
          Predicate locationId =
              criteriaBuilder.equal(
                  root.get("hall").get("location").get("id"), eventFilterDto.getLocationId());
          expressions.add(locationId);
        }
        if (eventFilterDto.getLocationName() != null) {
          Predicate locationName =
              criteriaBuilder.like(
                  root.get("hall").get("location").get("name"),
                  "%" + eventFilterDto.getLocationName() + "%");
          expressions.add(locationName);
        }

        if (eventFilterDto.getLocationPlace() != null) {
          Predicate locationPlace =
              criteriaBuilder.like(
                  root.get("hall").get("location").get("place"),
                  "%" + eventFilterDto.getLocationPlace() + "%");
          expressions.add(locationPlace);
        }
        if (eventFilterDto.getLocationCountry() != null) {
          Predicate locationCountry =
              criteriaBuilder.like(
                  root.get("hall").get("location").get("country"),
                  "%" + eventFilterDto.getLocationCountry() + "%");
          expressions.add(locationCountry);
        }
        if (eventFilterDto.getLocationStreet() != null) {
          Predicate locationStreet =
              criteriaBuilder.like(
                  root.get("hall").get("location").get("street"),
                  "%" + eventFilterDto.getLocationStreet() + "%");
          expressions.add(locationStreet);
        }
        if (eventFilterDto.getLocationPostalCode() != null) {
          Predicate locationPostalCode =
              criteriaBuilder.like(
                  root.get("hall").get("location").get("postalCode"),
                  "%" + eventFilterDto.getLocationId() + "%");
          expressions.add(locationPostalCode);
        }

        Predicate[] predicates = expressions.toArray(new Predicate[expressions.size()]);

        return criteriaBuilder.and(predicates);
      }
    };
  }

  /** Get the general specification for an event. */
  public static Specification<Event> getEventSpecification(EventFilterDto eventFilterDto) {
    Specification<Event> specification = UserSpecification.alwaysTrue();
    if (eventFilterDto.getName() != null) {
      specification =
          specification.and(UserSpecification.contains("name", eventFilterDto.getName()));
    }
    if (eventFilterDto.getDuration() != null) {
      specification =
          specification.and(
              UserSpecification.endures(
                  "duration", eventFilterDto.getDuration(), Duration.ofMinutes(30)));
    }
    if (eventFilterDto.getContent() != null) {
      specification =
          specification.and(UserSpecification.contains("content", eventFilterDto.getName()));
    }
    if (eventFilterDto.getEventCategory() != null) {
      specification =
          specification.and(
              UserSpecification.belongsTo("category", eventFilterDto.getEventCategory()));
    }
    return specification;
  }

  /** Javadoc. */
  public static Specification<Event> likeArtist(EventFilterDto eventFilterDto) {
    return new Specification<Event>() {
      @Override
      public Predicate toPredicate(
          Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> expressions = new ArrayList<>();

        if (eventFilterDto.getArtistName() != null) {
          Predicate hallName =
              criteriaBuilder.like(
                  root.get("artists").get("name"), "%" + eventFilterDto.getHallName() + "%");
          expressions.add(hallName);
        }
        Predicate[] predicates = expressions.toArray(new Predicate[expressions.size()]);

        return criteriaBuilder.and(predicates);
      }
    };
  }


}
