package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.NewsRead;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserNewsKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsReadRepository extends JpaRepository<NewsRead, UserNewsKey> {

  /**
   * Find all NewsRead entries for given user.
   *
   * @param user news read by
   * @return list of all NewsRead entries
   */
  List<NewsRead> findAllByUser(User user);

}
