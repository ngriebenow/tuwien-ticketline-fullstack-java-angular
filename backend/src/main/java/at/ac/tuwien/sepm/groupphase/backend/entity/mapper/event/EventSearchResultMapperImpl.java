package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventSearchResultMapperImpl implements EventSearchResultMapper {

  @Override
  public EventSearchResultDto eventToEventSearchResultDto(Event event) {
    EventSearchResultDto dto = new EventSearchResultDto();
    dto.setId(event.getId());
    dto.setName(event.getName());
    dto.setCategory(event.getCategory());
    dto.setHallName(event.getHall().getName());
    dto.setDuration(event.getDuration());
    dto.setLocationName(event.getHall().getLocation().getName());
    dto.setLocationPlace(event.getHall().getLocation().getPlace());

    return dto;
  }
}
