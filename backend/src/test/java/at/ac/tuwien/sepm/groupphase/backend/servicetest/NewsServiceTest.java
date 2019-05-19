package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class NewsServiceTest {

  @Autowired private NewsService newsService;
  @Autowired private UserRepository userRepository;

  private User U1;
  private User U2;

  private DetailedNewsDto N1 = DetailedNewsDto.builder()
      .id(1L)
      .title("Test")
      .summary("Just one Test")
      .text("abcdefghijk test news test news news").build();

  @Before
  public void initialization() {

    User admin = new User();
    admin.setUsername("admin");
    admin.setAuthority("ROLE_ADMIN");
    admin.setEnabled(true);
    admin.setFailedLoginCounter(0);
    admin.setPassword("password");

    User user = new User();
    user.setUsername("user");
    user.setAuthority("ROLE_USER");
    user.setEnabled(true);
    user.setFailedLoginCounter(0);
    user.setPassword("password");

    U1 = userRepository.saveAndFlush(admin);
    U2 = userRepository.saveAndFlush(user);

  }

  @Test
  public void givenUserAndNoNews_whenCreateNewsThenFind_thenReturnNews() {
    N1 = newsService.create(N1);
    DetailedNewsDto retN1 = newsService.findOne(N1.getId(), U1);
    assertThat(retN1).isEqualTo(N1);
  }

  @Test(expected = NotFoundException.class)
  public void givenNoNews_whenFindNewsById_thenThrowNotFoundException() {
    DetailedNewsDto retN1 = newsService.findOne(-1L, U1);
  }
}
