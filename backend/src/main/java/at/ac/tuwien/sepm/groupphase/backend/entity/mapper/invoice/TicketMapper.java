package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  @Mappings({
      @Mapping(target = "priceInCents", source = "ticket.definedUnit.priceCategory.priceInCents"),
      @Mapping(target = "title", source = "ticket.definedUnit.unit.name"),
      @Mapping(target = "invoiceId", source = "ticket.invoice.id"),
      @Mapping(target = "performanceId", source = "ticket.definedUnit.performance.id")
  })
  TicketDto ticketToTicketDto(Ticket ticket);
}
