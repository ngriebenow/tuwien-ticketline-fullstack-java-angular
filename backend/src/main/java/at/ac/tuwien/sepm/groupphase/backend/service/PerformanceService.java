package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PriceCategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
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
  List<DefinedUnitDto> getDefinedUnitsByPerformanceId(Performance performance) throws NotFoundException;

  /**
   * Get all price categories of an event.
   *
   * @param event containing only the event id
   * @return a list with all price categories of this event
   */
  List<PriceCategoryDto> getPriceCategoriesByEventId(Event event) throws NotFoundException;
}
