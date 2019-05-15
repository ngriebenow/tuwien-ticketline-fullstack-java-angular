package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;

public interface NewsService {
  /**
   * Find all news entries.
   *
   * @return list of all news entries
   */
  List<News> findAll();

  /**
   * Find a single detailed news entry by id.
   *
   * @param id the id of the news entry
   * @return the detailed news entry
   * @throws NotFoundException if the id could not be found
   */
  News findOne(Long id) throws NotFoundException;

  /**
   * Publish a single detailed news entry.
   *
   * @param news to publish
   * @return published detailed news entry
   */
  News create(News news);
}
