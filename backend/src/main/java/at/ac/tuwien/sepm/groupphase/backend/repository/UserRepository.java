package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  /**
   * Find a single user entry by id.
   *
   * @param username the id of the user entry
   * @return Optional containing the user entry
   */
  User findOneByUsername(String username);

  /**
   * Check if a user with given name is already present in the database.
   *
   * @param username of the user.
   * @return Whether a user with that name exists.
   */
  boolean existsByUsername(String username);
}
