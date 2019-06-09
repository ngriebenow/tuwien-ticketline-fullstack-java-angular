package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.LocationFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class LocationSpecification {

  /**
   * Return a specification containing all the criteria of not null filter attributes.
   */
  public static Specification<Location> buildFilterSpecification(
      LocationFilterDto locationFilterDto
  ) {
    return (root, query, criteriaBuilder) -> {
      Predicate conjunction = criteriaBuilder.conjunction();
      List<Expression<Boolean>> predicates = conjunction.getExpressions();

      if (locationFilterDto.getName() != null) {
        Predicate name =
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")),
                "%" + locationFilterDto.getName().toLowerCase() + "%");
        predicates.add(name);
      }

      if (locationFilterDto.getStreet() != null) {
        Predicate street =
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("street")),
                "%" + locationFilterDto.getStreet().toLowerCase() + "%");
        predicates.add(street);
      }

      if (locationFilterDto.getPlace() != null) {
        Predicate place =
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("place")),
                "%" + locationFilterDto.getPlace().toLowerCase() + "%");
        Predicate postalCode =
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("postalCode")),
                "%" + locationFilterDto.getPlace().toLowerCase() + "%");

        Predicate combinedPlacePostalCode =
            criteriaBuilder.or(place, postalCode);
        predicates.add(combinedPlacePostalCode);
      }

      if (locationFilterDto.getCountry() != null) {
        Predicate country =
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("country")),
                "%" + locationFilterDto.getCountry().toLowerCase() + "%");
        predicates.add(country);
      }

      return conjunction;
    };
  }
}
