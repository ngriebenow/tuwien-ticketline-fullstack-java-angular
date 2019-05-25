package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.definedunit;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DefinedUnitMapper {

  DefinedUnit definedUnitDtoToEntity(DefinedUnitDto definedUnitDto);

  DefinedUnitDto definedUnitToDto(DefinedUnit definedUnit);

}
