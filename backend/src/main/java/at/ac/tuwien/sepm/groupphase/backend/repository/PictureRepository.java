package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {

  /**
   * Find all pictures for given news entry.
   *
   * @param news entry to find pictures for.
   * @return list of all picture ids
   */
  List<Picture> findAllByNewsOrderByIdAsc(News news);

}
