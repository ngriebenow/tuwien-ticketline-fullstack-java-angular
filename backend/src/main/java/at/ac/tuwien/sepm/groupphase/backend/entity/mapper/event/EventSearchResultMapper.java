package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface EventSearchResultMapper {

  EventSearchResultDto eventToEventSearchResultDto(Event event);

}
