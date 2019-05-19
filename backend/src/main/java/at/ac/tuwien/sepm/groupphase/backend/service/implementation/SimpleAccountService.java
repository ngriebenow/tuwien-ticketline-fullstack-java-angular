package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.user.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.DuplicateEntry;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SimpleAccountService implements AccountService {

  private UserRepository userRepository;
  private UserMapper userMapper;

  public SimpleAccountService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public List<User> findAll(UserDto user) {
    return null;
  }

  @Override
  public User findOne(String id) {
    User u = userRepository.findOneByUsername(id);
    if (u == null) {
      throw new NotFoundException("User " + id + " not found!");
    }
    return u;
  }

  @Override
  public UserDto saveUser(UserDto user) {
    try {
      findOne(user.getUsername());
      throw new DuplicateEntry("User " + user.getUsername() + " already in database.");
    } catch (NotFoundException e) {
      // Everything fine
    }
    User u = userMapper.userDtoToUser(user);
    userRepository.saveAndFlush(u);
    return userMapper.userToUserDto(findOne(u.getUsername()));
  }

  @Override
  public UserDto editUser(UserDto user) {
    return null;
  }
}
