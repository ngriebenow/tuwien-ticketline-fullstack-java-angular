package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface NewsService {
  /**
   * Find all news entries.
   *
   * @param pageable for pagination
   * @param user to mark unread news
   * @return list of all simple news entries
   */
  List<SimpleNewsDto> findAll(User user, Pageable pageable);

  /**
   * Find all new (== not read) news entries for given user.
   *
   * @param user to find new news entry for
   * @param pageable for pagination
   * @return list of all simple news entries
   */
  List<SimpleNewsDto> findAllNew(User user, Pageable pageable);
  /**
   * Find a single detailed news entry by id.
   *
   * @param id the id of the news entry
   * @param user to mark the news as read
   * @return the detailed news entry
   * @throws NotFoundException if the id could not be found
   */

  DetailedNewsDto findOne(Long id, User user) throws NotFoundException;

  /**
   * Publish a single detailed news entry.
   *
   * @param news to publish
   * @return published detailed news entry
   */
  DetailedNewsDto create(DetailedNewsDto news);
}
