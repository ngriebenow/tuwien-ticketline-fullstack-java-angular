package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import org.mapstruct.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class InvoiceParentMapper {

  @Qualifier
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.SOURCE)
  public @interface ParentInvoicePaidAt {
  }

  @ParentInvoicePaidAt
  public LocalDate getParentPaidAt(Invoice parentInvoice) {
    return parentInvoice == null ? null : parentInvoice.getPaidAt();
  }

  @Qualifier
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.SOURCE)
  public @interface ParentInvoiceNumber {
  }

  @ParentInvoiceNumber
  public Long getParentInvoiceNumber(Invoice parentInvoice) {
    return parentInvoice == null ? null : parentInvoice.getNumber();
  }

}
