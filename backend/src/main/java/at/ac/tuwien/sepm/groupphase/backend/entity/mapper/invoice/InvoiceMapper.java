package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.client.ClientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
    componentModel = "spring",
    uses = {TicketMapper.class, ClientMapper.class, InvoiceParentMapper.class})
public interface InvoiceMapper {

  @Mappings({
      @Mapping(
          target = "parentNumber",
          source = "parentInvoice",
          qualifiedBy = InvoiceParentMapper.ParentInvoiceNumber.class),
      @Mapping(
          target = "parentPaidAt",
          source = "parentInvoice",
          qualifiedBy = InvoiceParentMapper.ParentInvoicePaidAt.class)
  })
  InvoiceDto invoiceToInvoiceDto(Invoice invoice);
}
