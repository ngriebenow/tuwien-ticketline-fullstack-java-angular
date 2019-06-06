package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository
    extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

  Optional<Invoice> findByIdAndIsPaid(Long id, boolean isPaid);

  Optional<Invoice> findByIdAndIsPaidAndIsCancelled(Long id, boolean isPaid, boolean isCancelled);

  List<Invoice> findByIsPaidAndIsCancelled(boolean isPaid, boolean isCancelled);

  boolean existsByParentInvoice(Invoice parentInvoice);
}
