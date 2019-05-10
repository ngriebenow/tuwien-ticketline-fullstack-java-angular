package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Find a single user entry by id.
     *
     * @param username the id of the user entry
     * @return Optional containing the user entry
     */
    User findOneByUsername(String username);

}
