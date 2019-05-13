package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;

public interface NewsService {
  /**
   * Find all news entries.
   *
   * @return list of all simple news entries
   */
  List<SimpleNewsDto> findAll();

  /**
   * Find a single detailed news entry by id.
   *
   * @param id the id of the news entry
   * @return the detailed news entry
   * @throws NotFoundException if the id could not be found
   */
  DetailedNewsDto findOne(Long id) throws NotFoundException;

  /**
   * Publish a single detailed news entry.
   *
   * @param news to publish
   * @return published detailed news entry
   */
  DetailedNewsDto create(DetailedNewsDto news);
}
