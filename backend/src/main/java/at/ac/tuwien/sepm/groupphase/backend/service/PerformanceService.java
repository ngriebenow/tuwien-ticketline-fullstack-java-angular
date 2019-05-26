package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;

public interface PerformanceService {

  /**
   * Get all defined units of a performance.
   *
   * @param performance containing only the performance id
   * @return a list with all defined units of this performance
   */
  List<DefinedUnitDto> getDefinedUnitsByPerformanceId(Performance performance)
      throws NotFoundException;
}
