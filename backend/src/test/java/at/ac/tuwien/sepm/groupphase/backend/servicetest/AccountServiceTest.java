package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.DuplicateEntry;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "unit-test")
public class AccountServiceTest {

  @Autowired
  private AccountService accountService;
  @Autowired
  private UserRepository userRepository;

  private static final String somePasswd = "ABCDEFGHIJKLMNOP";
  private UserDto u1, u2, u3;

  @Before
  public void setUp() {
    u1 = new UserDto();
    u1.setUsername("U1");
    u1.setPassword(somePasswd); // Doesnt matter in this test -> never returned from service
    u1.setEnabled(true);
    u1.setFailedLoginCounter(0);
    u1.setAdmin(true);

    u2 = new UserDto();
    u2.setUsername("U2");
    u2.setPassword(somePasswd);
    u2.setEnabled(true);
    u2.setFailedLoginCounter(0);
    u2.setAdmin(false);

    u3 = new UserDto();
    u3.setUsername("U3");
    u3.setPassword(somePasswd);
    u3.setEnabled(false);
    u3.setFailedLoginCounter(0);
    u3.setAdmin(false);

    accountService.saveUser(u1);
    accountService.saveUser(u2);
    accountService.saveUser(u3);
  }

  @Test(expected = NotFoundException.class)
  public void givenNothing_whenGetById_thenNotFoundException() {
    accountService.getOneById("userthatshouldnotbeinanemptydatabase");
  }

  @Test(expected = NotFoundException.class)
  public void whenGetByNullId_thenNotFoundException() {
    accountService.getOneById(null);
  }

  @Test
  public void givenOneUser_whenGetById_thenUserDtoReturned() {
    UserDto tmp = accountService.getOneById(u1.getUsername());
    assertThat(tmp.equals(u1));
  }

  @Test(expected = DuplicateEntry.class)
  public void givenOneUser_throwExceptionOnDoubleSave() {
    accountService.saveUser(u1);
  }

  @After
  public void tearDown() {
    userRepository.deleteAll();
  }
}
