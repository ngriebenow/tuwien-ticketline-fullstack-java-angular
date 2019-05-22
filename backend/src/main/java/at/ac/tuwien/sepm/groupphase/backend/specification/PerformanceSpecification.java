package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event_;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance_;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class PerformanceSpecification {

  private static final int TIME_TOLERANCE = 30;


  /** Javadoc. */
  public static Specification<Performance> like(long eventId, EventFilterDto eventFilterDto) {

    Specification<Performance> eventSpecification = new Specification<Performance>() {
      @Override
      public Predicate toPredicate(Root<Performance> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(
            root.get("event").get("id"), eventId
        );
      }
    };

    Specification<Performance> timeSpecification = new Specification<Performance>() {
      @Override
      public Predicate toPredicate(
          Root<Performance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {


        if (eventFilterDto.getStartAtTime() != null) {
          return criteriaBuilder.between(
              criteriaBuilder.function(
                  "time", Time.class,root.get(Performance_.startAt)),
              Time.valueOf(eventFilterDto.getStartAtTime().minusMinutes(TIME_TOLERANCE).toLocalTime()),
              Time.valueOf(eventFilterDto.getStartAtTime().plusMinutes(TIME_TOLERANCE).toLocalTime()));


        }
        return criteriaBuilder.and();
      }
    };


    Specification<Performance> dateSpecification = new Specification<Performance>() {
      @Override
      public Predicate toPredicate(
          Root<Performance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (eventFilterDto.getStartAtDate() != null) {
          return criteriaBuilder.equal(
              criteriaBuilder.function(
                  "date", Time.class,root.get(Performance_.startAt)),
              Date.valueOf(eventFilterDto.getStartAtDate().toLocalDate()));
        }
        return criteriaBuilder.and();
      }
    };

    return eventSpecification.and(dateSpecification.and(timeSpecification));
  }

}
