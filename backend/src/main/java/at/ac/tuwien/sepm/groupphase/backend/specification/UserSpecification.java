package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import com.google.common.base.Predicates;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification<T> implements Specification<T> {

  /** Javadoc. */
  public static <T> Specification<T> contains(String name, String key) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(
          Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(name), "%" + key + "%");
      }
    };
  }

  /** Javadoc. */
  public static <T> Specification<T> startsAt(
      String name, LocalDateTime dateTime, Duration tolerance) {
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

  /** Javadoc. */
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

  /** Javadoc. */
  public static <T> Specification<T> belongsTo(String name, EventCategory eventCategory) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(
          Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.equal(root.get(name),eventCategory);

      }
    };
  }

  /** Javadoc. */
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