package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance_;
import java.time.ZonedDateTime;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PerformanceSpecification {

  private static final int TIME_TOLERANCE = 30;

  /**
   * Javadoc.
   */
  public static Specification<Performance> like(long eventId, EventFilterDto eventFilterDto) {

    Specification<Performance> eventSpecification =
        new Specification<Performance>() {
          @Override
          public Predicate toPredicate(
              Root<Performance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.equal(root.get("event").get("id"), eventId);
          }
        };

    Specification<Performance> timeSpecification =
        new Specification<Performance>() {
          @Override
          public Predicate toPredicate(
              Root<Performance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

            if (eventFilterDto.getStartAtTime() != null) {

              int totalDayMinutes =
                  eventFilterDto.getStartAtTime().getHour() * 60
                      + eventFilterDto.getStartAtTime().getMinute();
              int lowerBound = Math.max(0, totalDayMinutes - TIME_TOLERANCE);
              int upperBound = Math.min(24 * 60, totalDayMinutes + TIME_TOLERANCE);

              if (eventFilterDto.getStartAtTime() != null) {
                return criteriaBuilder.between(
                    criteriaBuilder.sum(
                        criteriaBuilder.prod(
                            criteriaBuilder.function(
                                "hour", Integer.class, root.get(Performance_.startAt)),
                            60),
                        criteriaBuilder.function(
                            "minute", Integer.class, root.get(Performance_.startAt))),
                    lowerBound,
                    upperBound);
              }
            }

            return criteriaBuilder.and();
          }
        };

    Specification<Performance> dateSpecification =
        new Specification<Performance>() {
          @Override
          public Predicate toPredicate(
              Root<Performance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

            if (eventFilterDto.getStartAtDate() != null) {
              return criteriaBuilder.and(
                  criteriaBuilder.equal(
                      criteriaBuilder.function(
                          "day", Integer.class, root.get(Performance_.startAt)),
                      eventFilterDto.getStartAtDate().getDayOfMonth()),
                  criteriaBuilder.equal(
                      criteriaBuilder.function(
                          "month", Integer.class, root.get(Performance_.startAt)),
                      eventFilterDto.getStartAtDate().getMonthValue()),
                  criteriaBuilder.equal(
                      criteriaBuilder.function(
                          "year", Integer.class, root.get(Performance_.startAt)),
                      eventFilterDto.getStartAtDate().getYear()));
            } else {
              return criteriaBuilder.greaterThanOrEqualTo(root.get(Performance_.startAt),
                  ZonedDateTime.now());
            }
          }
        };

    return eventSpecification.and(dateSpecification.and(timeSpecification));
  }
}
