package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventRankingDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventRanking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventRankingMapper {

  EventRanking eventRankingDtoToEventRanking(EventRankingDto eventDto);

  EventRankingDto eventRankingToEventRankingDto(EventRanking event);
}
