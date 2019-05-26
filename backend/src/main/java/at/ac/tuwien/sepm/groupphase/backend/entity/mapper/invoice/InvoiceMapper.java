package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.client.ClientMapper;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {TicketMapper.class, ClientMapper.class})
public interface InvoiceMapper {

  InvoiceDto invoiceToInvoiceDto(Invoice invoice);
}
