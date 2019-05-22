package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event_;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {

  private static final int PRICE_TOLERANCE = 1000;

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
                  criteriaBuilder.lower(root.get("hall").get("name")),
                  "%" + eventFilterDto.getHallName().toLowerCase() + "%");
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
                  criteriaBuilder.lower(root.get("hall").get("location").get("id")),
                  eventFilterDto.getLocationId());
          expressions.add(locationId);
        }
        if (eventFilterDto.getLocationName() != null) {
          Predicate locationName =
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("hall").get("location").get("name")),
                  "%" + eventFilterDto.getLocationName().toLowerCase() + "%");
          expressions.add(locationName);
        }

        if (eventFilterDto.getLocationPlace() != null) {
          Predicate locationPlace =
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("hall").get("location").get("place")),
                  "%" + eventFilterDto.getLocationPlace().toLowerCase() + "%");
          expressions.add(locationPlace);
        }
        if (eventFilterDto.getLocationCountry() != null) {
          Predicate locationCountry =
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("hall").get("location").get("country")),
                  "%" + eventFilterDto.getLocationCountry().toLowerCase() + "%");
          expressions.add(locationCountry);
        }
        if (eventFilterDto.getLocationStreet() != null) {
          Predicate locationStreet =
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("hall").get("location").get("street")),
                  "%" + eventFilterDto.getLocationStreet().toLowerCase() + "%");
          expressions.add(locationStreet);
        }
        if (eventFilterDto.getLocationPostalCode() != null) {
          Predicate locationPostalCode =
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("hall").get("location").get("postalCode")),
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
          specification.and(
              UserSpecification.contains("name", eventFilterDto.getName().toLowerCase()));
    }
    if (eventFilterDto.getDuration() != null) {
      specification =
          specification.and(
              UserSpecification.endures(
                  "duration", eventFilterDto.getDuration(), Duration.ofMinutes(30)));
    }
    if (eventFilterDto.getContent() != null) {
      specification =
          specification.and(
              UserSpecification.contains("content", eventFilterDto.getContent().toLowerCase()));
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


        if (eventFilterDto.getArtistName() != null) {

          query.distinct(true);
          ListJoin<Event, Artist> eaJoin = root.join(Event_.artists, JoinType.LEFT);

          Predicate artistName =
              criteriaBuilder.like(criteriaBuilder.lower(
                  eaJoin.get("name")), "%" + eventFilterDto.getArtistName().toLowerCase() + "%");
          Predicate artistSurname =
              criteriaBuilder.like(criteriaBuilder.lower(
                  eaJoin.get("surname")), "%" + eventFilterDto.getArtistName().toLowerCase() + "%");

          return criteriaBuilder.or(artistName,artistSurname);

        }
        return criteriaBuilder.and();
      }
    };
  }

  /** Javadoc. */
  public static Specification<Event> likePrice(EventFilterDto eventFilterDto) {
    return new Specification<Event>() {
      @Override
      public Predicate toPredicate(
          Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (eventFilterDto.getPriceInCents() != null) {

          Subquery<PriceCategory> subquery = query.subquery(PriceCategory.class);

          Root<PriceCategory> pc = subquery.from(PriceCategory.class);

          subquery.where(criteriaBuilder.and(
              criteriaBuilder.equal(pc.get("event").get("id"),root.get("id")),
              criteriaBuilder.between(pc.get("priceInCents"),
                  eventFilterDto.getPriceInCents() - PRICE_TOLERANCE,
                  eventFilterDto.getPriceInCents() + PRICE_TOLERANCE)));

          subquery.select(pc);

          return criteriaBuilder.exists(subquery);

        }
        return criteriaBuilder.and();
      }
    };
  }
}
