package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.definedunit.DefinedUnitMapperImpl;
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
  private DefinedUnitMapperImpl definedUnitMapper;

  /**
   * Gets all defined units for a performance by its id.
   *
   * @param performance containing only the performance id
   */
  @Transactional(readOnly = true)
  @Override
  public List<DefinedUnitDto> getDefinedUnitsByPerformanceId(
      final Performance performance) throws NotFoundException {
    LOGGER.info("getDefinedUnitsByPerformanceId " + performance.getId());
    List<DefinedUnitDto> definedUnitDtos = new ArrayList<>();

    List<DefinedUnit> definedUnits =
        definedUnitRepository.findAllByPerformanceIsLike(performance);

    if (definedUnits.size() == 0) {
      throw new NotFoundException("Could not find defined units.");
    }

    for (DefinedUnit definedUnit : definedUnits) {
      DefinedUnitDto definedUnitDto =
          definedUnitMapper.definedUnitToDto(definedUnit);
      definedUnitDtos.add(definedUnitDto);
    }
    return definedUnitDtos;
  }
}
