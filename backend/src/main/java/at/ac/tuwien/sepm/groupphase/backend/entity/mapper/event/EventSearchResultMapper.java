package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

// @Mapper(componentModel = "spring")
public interface EventSearchResultMapper {

  EventSearchResultDto eventToEventSearchResultDto(Event event);
}
