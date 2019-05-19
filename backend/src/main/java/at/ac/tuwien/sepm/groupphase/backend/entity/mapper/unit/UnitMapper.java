package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.unit;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitMapper {

  Unit unitDtoToUnit(UnitDto unitDto);

  UnitDto unitToUnitDto(Unit unit);
}
