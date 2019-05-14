package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface InvoiceMapper {

  InvoiceDto invoiceToInvoiceDto(Invoice invoice);
}
