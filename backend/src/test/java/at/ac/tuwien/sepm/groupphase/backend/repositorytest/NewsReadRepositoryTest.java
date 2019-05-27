package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsRead;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserNewsKey;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsReadRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NewsReadRepositoryTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  NewsReadRepository newsReadRepository;
  @Autowired
  NewsRepository newsRepository;

  private News N1 = new News.Builder().
      id(0L).
      publishedAt(LocalDateTime.now()).
      title("Test").
      summary("Just one Test").
      text("abcdefghijk test news test news news").build();

  private User U1;
  private User U2;
  private NewsRead NR1;

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

    N1 = newsRepository.save(N1);

    NR1 = new NewsRead.Builder().
        news(N1).
        user(U1).
        build();

    NR1 = newsReadRepository.save(NR1);

  }

  @Test
  public void givenNewsReadSaved_whenFindNewsReadByUser_thenReturnNewsRead() {
    List<NewsRead> retNR = newsReadRepository.findAllByUser(U1);
    assertThat(retNR.size()).isEqualTo(1);
    assertThat(retNR.get(0)).isEqualTo(NR1);
  }


  @Test
  public void givenNewsReadSaved_whenFindNewsReadByUserNotReadAny_thenReturnEmptyList() {
    List<NewsRead> retNR = newsReadRepository.findAllByUser(U2);
    assertThat(retNR.size()).isEqualTo(0);

  }

  @Test(expected = NotFoundException.class)
  public void givenNewsReadSaved_whenFindUnknownNewsReadById_thenThrowNotFoundException() {
    UserNewsKey NRU = new UserNewsKey.Builder().
        user("unknown").
        news(-1L).
        build();
    newsReadRepository.findById(NRU).orElseThrow(NotFoundException::new);
  }


}

