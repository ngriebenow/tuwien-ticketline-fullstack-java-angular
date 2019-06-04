package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import java.time.Duration;
import java.time.ZonedDateTime;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification<T> implements Specification<T> {

  /**
   * Javadoc.
   */
  public static <T> Specification<T> contains(String name, String key) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(
          Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(
            criteriaBuilder.lower(root.get(name)), "%" + key.toLowerCase() + "%");
      }
    };
  }

  /**
   * Javadoc.
   */
  public static <T> Specification<T> startsAt(
      String name, ZonedDateTime dateTime, Duration tolerance) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(
          Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate leq = criteriaBuilder.lessThanOrEqualTo(root.get(name), dateTime.plus(tolerance));
        Predicate geq =
            criteriaBuilder.greaterThanOrEqualTo(root.get(name), dateTime.minus(tolerance));

        return criteriaBuilder.and(leq, geq);
      }
    };
  }

  /**
   * Javadoc.
   */
  public static <T> Specification<T> endures(String name, Duration duration, Duration tolerance) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(
          Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate leq = criteriaBuilder.lessThanOrEqualTo(root.get(name), duration.plus(tolerance));
        Predicate geq =
            criteriaBuilder.greaterThanOrEqualTo(root.get(name), duration.minus(tolerance));

        return criteriaBuilder.and(leq, geq);
      }
    };
  }

  /**
   * Javadoc.
   */
  public static <T> Specification<T> belongsTo(String name, EventCategory eventCategory) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(
          Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.equal(root.get(name), eventCategory);
      }
    };
  }

  /**
   * Javadoc.
   */
  public static <T> Specification<T> alwaysTrue() {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(
          Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and();
      }
    };
  }

  @Override
  public Predicate toPredicate(
      Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    return null;
  }
}
