package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

  /**
   * Find a single event entry by id.
   *
   * @param id the is of the event entry
   * @return Optional containing the event entry
   */
  Optional<Artist> findOneById(Long id);

}
