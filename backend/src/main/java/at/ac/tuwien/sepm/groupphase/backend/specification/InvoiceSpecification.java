package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.InvoiceFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceSpecification {

  public static Specification<Invoice> buildFilterSpecification(InvoiceFilterDto invoiceFilterDto) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      invoiceFilterDto
          .getIsPaid()
          .ifPresent(isPaid -> predicates.add(criteriaBuilder.equal(root.get("isPaid"), isPaid)));

      invoiceFilterDto
          .getIsCancelled()
          .ifPresent(
              isCancelled ->
                  predicates.add(criteriaBuilder.equal(root.get("isCancelled"), isCancelled)));

      invoiceFilterDto
          .getReservationCode()
          .ifPresent(
              reservationCode ->
                  predicates.add(
                      criteriaBuilder.like(
                          criteriaBuilder.lower(root.get("reservationCode")),
                          ilike(reservationCode))));

      invoiceFilterDto
          .getClientName()
          .ifPresent(
              clientName -> predicates.add(likeClientName(clientName, root, criteriaBuilder)));

      invoiceFilterDto
          .getClientEmail()
          .ifPresent(
              clientEmail ->
                  predicates.add(
                      criteriaBuilder.like(
                          criteriaBuilder.lower(root.get("client").get("email")),
                          ilike(clientEmail))));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  private static String ilike(String fragment) {
    return "%" + fragment.toLowerCase() + "%";
  }

  private static Predicate likeClientName(
      String clientName, Root<Invoice> root, CriteriaBuilder criteriaBuilder) {
    String[] tokens = clientName.split(" ");
    Predicate[] predicates = new Predicate[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      predicates[i] =
          criteriaBuilder.or(
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("client").get("name")), ilike(tokens[i])),
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("client").get("surname")), ilike(tokens[i])));
    }
    return criteriaBuilder.and(predicates);
  }
}
