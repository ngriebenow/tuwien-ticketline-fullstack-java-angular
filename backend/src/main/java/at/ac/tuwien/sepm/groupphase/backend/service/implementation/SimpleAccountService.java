package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.UserFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.user.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.InvalidInputException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleAccountService implements AccountService {

  private UserRepository userRepository;
  private UserMapper userMapper;

  public SimpleAccountService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  /** Javadoc. */
  private static Specification<User> likeUser(UserFilterDto userFilter) {
    return new Specification<User>() {
      @Override
      public Predicate toPredicate(
          Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> expressions = new ArrayList<>();

        if (userFilter.getUsername() != null) {
          Predicate username =
              criteriaBuilder.like(root.get("username"), "%" + userFilter.getUsername() + "%");
          expressions.add(username);
        }
        if (userFilter.getRole() != null) {
          String rolestr = "";
          if (userFilter.getRole().toLowerCase().contains("admin")) {
            rolestr = "ROLE_ADMIN";
          } else if (userFilter.getRole().toLowerCase().contains("user")) {
            rolestr = "ROLE_USER";
          } else {
            throw new InvalidInputException("Undefined role: " + userFilter.getRole());
          }
          Predicate role = criteriaBuilder.like(root.get("authority"), "%" + rolestr + "%");
          expressions.add(role);
        }
        if (userFilter.getLocked() != null) {
          if (userFilter.getLocked().toLowerCase().contains("true")) {
            Predicate enabled = criteriaBuilder.equal(root.get("enabled"), false);
            expressions.add(enabled);
          } else if (userFilter.getLocked().toLowerCase().contains("false")) {
            Predicate enabled = criteriaBuilder.equal(root.get("enabled"), true);
            expressions.add(enabled);
          }
        }

        Predicate[] predicates = expressions.toArray(new Predicate[expressions.size()]);

        return criteriaBuilder.and(predicates);
      }
    };
  }

  @Transactional(readOnly = true)
  @Override
  public List<UserDto> findAll(UserFilterDto user) {
    if (user.getCount() == null) {
      user.setCount(10);
    }
    if (user.getPage() == null) {
      user.setPage(0);
    }
    if (user.getCount() <= 0) {
      throw new InvalidInputException("Count must be >0");
    }
    if (user.getPage() < 0) {
      throw new InvalidInputException("Page must be >0");
    }
    if (user.getUsername() != null && user.getUsername().isEmpty()) {
      user.setUsername(null);
    }
    if (user.getRole() != null && user.getRole().isEmpty()) {
      user.setRole(null);
    }
    if (user.getLocked() != null && user.getLocked().isEmpty()) {
      user.setLocked(null);
    }

    Pageable pageable = PageRequest.of(user.getPage(), user.getCount());
    Specification<User> userSpecification = likeUser(user);
    Page<User> users = userRepository.findAll(userSpecification, pageable);
    ArrayList<UserDto> toret = new ArrayList<>();
    users.forEach(e -> toret.add(userMapper.userToUserDto(e)));
    return toret;
  }

  @Transactional(readOnly = true)
  @Override
  public User findOne(String id) {
    User u = userRepository.findOneByUsername(id);
    if (u == null) {
      throw new NotFoundException("User " + id + " not found!");
    }
    return u;
  }

  @Transactional
  @Override
  public UserDto saveUser(UserDto user) {
    try {
      findOne(user.getUsername());
      throw new ValidationException("User " + user.getUsername() + " already in database.");
    } catch (NotFoundException e) {
      // Everything fine
    }
    User u;
    try {
      u = userMapper.userDtoToUser(user);
    } catch (NullPointerException e) {
      throw new InvalidInputException("All fields must be set!");
    }
    userRepository.saveAndFlush(u);
    return userMapper.userToUserDto(findOne(u.getUsername()));
  }

  private User updateUserHelper(User old, UserDto updated) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (updated.getPassword() != null) {
      old.setPassword(encoder.encode(updated.getPassword()));
    }
    if (updated.getFailedLoginCounter() != null) {
      old.setFailedLoginCounter(updated.getFailedLoginCounter());
    }
    if (updated.getEnabled() != null) {
      old.setEnabled(updated.getEnabled().toLowerCase().equals("true") ? true : false);
    }
    if (updated.getAdmin() != null) {
      old.setAuthority(
          updated.getAdmin().toLowerCase().equals("true") ? "ROLE_ADMIN" : "ROLE_USER");
    }
    return old;
  }

  @Transactional
  @Override
  public UserDto editUser(UserDto user) {
    if (user.getUsername() == null) {
      throw new InvalidInputException("Username must be set!");
    }
    if(user.getPassword()!=null&&user.getPassword().isEmpty()){
      user.setPassword(null);
    }
    User old = findOne(user.getUsername()); //Will throw NotFoundException if not in DB
    old = updateUserHelper(old, user);
    userRepository.saveAndFlush(old);
    return getOneById(user.getUsername());
  }

  @Transactional(readOnly = true)
  @Override
  public UserDto getOneById(String id) {
    return userMapper.userToUserDto(findOne(id));
  }

}
