package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PerformanceService {

  /**
   * Get the performance by id.
   *
   * @param id the id of the per
   * @return the performance
   * @throws NotFoundException if the id could not be found
   */
  PerformanceDto getOneById(Long id) throws NotFoundException;

  /**
   * Get all performances which satisfy the given constraints in specification.
   *
   * @param specification the search criteria which all returned performances fulfill
   * @param pageable the pageable for determing the page
   * @return the list of performances
   */
  List<PerformanceDto> getPerformancesFiltered(
      Specification<Performance> specification, Pageable pageable);
}
