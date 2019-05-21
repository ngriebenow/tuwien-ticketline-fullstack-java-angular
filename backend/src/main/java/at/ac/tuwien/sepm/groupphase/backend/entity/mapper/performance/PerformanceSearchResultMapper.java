package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerformanceSearchResultMapper {

  PerformanceSearchResultDto performanceToPerformanceSearchResultDto(Performance performance);
}
