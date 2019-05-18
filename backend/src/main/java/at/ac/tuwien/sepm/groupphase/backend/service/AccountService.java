package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;

import java.util.List;

public interface AccountService {

  /**
   * Find all user entries.
   *
   * @return list of all user entries
   */
  List<User> findAll(UserDto user);

  /**
   * Find a single user entry by id.
   *
   * @param id the name of the user entry
   * @return the user entry
   */
  User findOne(String id);

  /**
   * Store user in database.
   * @param user the user to store
   * @return user read from database.
   */
  UserDto saveUser(UserDto user);

  /**
   * Edit user and store new user in database.
   * @param user the user with edited attributes.
   * @return user read from database.
   */
  UserDto editUser(UserDto user);
}
