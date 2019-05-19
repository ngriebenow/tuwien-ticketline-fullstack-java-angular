package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

  /**
   * Find all news entries ordered by published at date (descending).
   *
   * @param pageable for pagination
   * @return ordered list of all message entries
   */
  List<News> findAllByOrderByPublishedAtDesc(Pageable pageable);

}
