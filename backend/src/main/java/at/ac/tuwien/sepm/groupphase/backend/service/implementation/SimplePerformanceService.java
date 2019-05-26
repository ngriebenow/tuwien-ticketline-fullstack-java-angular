package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.definedunit.DefinedUnitMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.point.PointMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimplePerformanceService implements PerformanceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceService.class);
  @Autowired
  private DefinedUnitRepository definedUnitRepository;
  @Autowired
  private DefinedUnitMapper definedUnitMapper;
  @Autowired
  private PointMapper pointMapper;

  @Transactional(readOnly = true)
  @Override
  public List<DefinedUnitDto> getDefinedUnitsByPerformanceId(Performance performance)
      throws NotFoundException {
    LOGGER.info("getDefinedUnitsByPerformanceId " + performance.getId());
    List<DefinedUnitDto> definedUnitDtos = new ArrayList<>();
    try {

      List<DefinedUnit> definedUnits =
          definedUnitRepository.findAllByPerformanceIsLike(performance);
      for (DefinedUnit definedUnit : definedUnits) {

        DefinedUnitDto definedUnitDto = definedUnitMapper.definedUnitToDto(definedUnit);
        definedUnitDto.setLowerBoundary(
            pointMapper.pointToPointDto(definedUnit.getUnit().getLowerBoundary()));

        definedUnitDto.setUpperBoundary(
            pointMapper.pointToPointDto(definedUnit.getUnit().getUpperBoundary()));

        definedUnitDto.setCapacity(definedUnit.getUnit().getCapacity());

        definedUnitDto.setName(definedUnit.getUnit().getName());

        // TODO: refactor so that free and capacityFree is free
        definedUnitDto.setFree(definedUnit.getCapacityFree());

        definedUnitDto.setPriceCategoryId(definedUnit.getPriceCategory().getId());

        definedUnitDtos.add(definedUnitDto);
      }
    } catch (NotFoundException e) {
      LOGGER.error("Could not get defined units " + e.getMessage());
    }
    return definedUnitDtos;
  }
}
