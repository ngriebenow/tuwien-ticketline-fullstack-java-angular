package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.invoice;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  @Mappings({
      @Mapping(target = "title", source = "ticket.definedUnit.unit.name"),
      @Mapping(target = "eventName", source = "ticket.definedUnit.performance.event.name"),
      @Mapping(target = "performanceName", source = "ticket.definedUnit.performance.name"),
      @Mapping(target = "startAt", source = "ticket.definedUnit.performance.startAt"),
      @Mapping(target = "priceCategoryName", source = "ticket.definedUnit.priceCategory.name"),
      @Mapping(target = "priceInCents", source = "ticket.definedUnit.priceCategory.priceInCents"),
      @Mapping(target = "locationName", source = "ticket.definedUnit.unit.hall.location.name"),
      @Mapping(target = "hallName", source = "ticket.definedUnit.unit.hall.name"),
      @Mapping(target = "definedUnitId", source = "ticket.definedUnit.id"),
      @Mapping(target = "performanceId", source = "ticket.definedUnit.performance.id"),
  })
  TicketDto ticketToTicketDto(Ticket ticket);
}
