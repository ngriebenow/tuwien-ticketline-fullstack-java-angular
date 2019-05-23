package at.ac.tuwien.sepm.groupphase.backend.specification;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.InvoiceFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceSpecification {

  /** Return a specification containing all the criteria of not null filter attributes. */
  public static Specification<Invoice> buildFilterSpecification(InvoiceFilterDto invoiceFilterDto) {
    return (root, query, criteriaBuilder) -> {
      Predicate conjunction = criteriaBuilder.conjunction();
      List<Expression<Boolean>> predicates = conjunction.getExpressions();

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

      invoiceFilterDto
          .getPerformanceName()
          .ifPresent(
              performanceName ->
                  predicates.add(
                      likeEventOrPerformanceName(performanceName, root, query, criteriaBuilder)));

      return conjunction;
    };
  }

  private static String ilike(String fragment) {
    return "%" + fragment.trim().toLowerCase() + "%";
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

  private static Predicate likeEventOrPerformanceName(
      String name, Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

    query.distinct(true);

    ListJoin<Invoice, Ticket> ticketJoin = root.joinList("tickets", JoinType.LEFT);

    Predicate performanceNamePredicate =
        criteriaBuilder.like(
            criteriaBuilder.lower(ticketJoin.get("definedUnit").get("performance").get("name")),
            ilike(name));

    Predicate eventNamePredicate =
        criteriaBuilder.like(
            criteriaBuilder.lower(
                ticketJoin.get("definedUnit").get("performance").get("event").get("name")),
            ilike(name));

    return criteriaBuilder.or(performanceNamePredicate, eventNamePredicate);
  }
}
