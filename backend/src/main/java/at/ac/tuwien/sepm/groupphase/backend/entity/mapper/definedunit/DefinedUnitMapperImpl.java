package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.definedunit;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.point.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefinedUnitMapperImpl implements DefinedUnitMapper{

  @Autowired
  private PointMapper pointMapper;

  @Override
  public DefinedUnitDto definedUnitToDto(DefinedUnit definedUnit) {
    DefinedUnitDto dto = new DefinedUnitDto();
    dto.setName(definedUnit.getUnit().getName());
    dto.setId(definedUnit.getId());
    dto.setFree(definedUnit.getCapacityFree());
    dto.setCapacity(definedUnit.getUnit().getCapacity());
    dto.setLowerBoundary(
        pointMapper.pointToPointDto(definedUnit.getUnit().getLowerBoundary()));
    dto.setUpperBoundary(
        pointMapper.pointToPointDto(definedUnit.getUnit().getUpperBoundary()));
    dto.setPriceCategoryId(definedUnit.getPriceCategory().getId());

    return dto;
  }
}
