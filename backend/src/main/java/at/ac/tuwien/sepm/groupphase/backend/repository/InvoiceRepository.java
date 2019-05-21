package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  @Query(
      "SELECT i"
          + " FROM Invoice i"
          + " WHERE"
          + " (:reservationCode IS NULL"
          + " OR"
          + " LOWER(i.reservationCode) LIKE LOWER(CONCAT('%', :reservationCode, '%')))"
          + " AND"
          + " (:isPaid IS NULL OR i.isPaid = :isPaid)"
          + " AND"
          + " (:isCancelled IS NULL OR i.isCancelled = :isCancelled)"
  )
  List<Invoice> findByFilter(
      @Param("reservationCode") String reservationCode,
      @Param("isPaid") boolean isPaid,
      @Param("isCancelled") boolean isCancelled,
      // @Param("clientName") String clientName,
      // @Param("clientEmail") String clientEmail,
      // @Param("customerEmail") String customerEmail,
      Pageable pageable);
}
