package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.definedunit;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;

//@Mapper(componentModel = "spring")
public interface DefinedUnitMapper {

  DefinedUnitDto definedUnitToDto(DefinedUnit definedUnit);

}
