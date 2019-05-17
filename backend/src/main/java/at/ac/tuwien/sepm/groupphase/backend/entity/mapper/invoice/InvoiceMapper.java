package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
    componentModel = "spring",
    uses = {TicketMapper.class})
public interface InvoiceMapper {

  @Mappings({
      @Mapping(target = "clientId", source = "invoice.client.id"),
  })
  InvoiceDto invoiceToInvoiceDto(Invoice invoice);
}
