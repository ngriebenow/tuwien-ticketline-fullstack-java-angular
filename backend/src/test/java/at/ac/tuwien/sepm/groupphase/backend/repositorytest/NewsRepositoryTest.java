package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NewsRepositoryTest {

  @Autowired
  NewsRepository newsRepository;

  private News N1 = new News.Builder().
      id(0L).
      publishedAt(LocalDateTime.now()).
      title("Test").
      summary("Just one Test").
      text("abcdefghijk test news test news news").build();

  private News N2 = new News.Builder().
      id(1L).
          publishedAt(LocalDateTime.now()).
          title("Test2").
          summary("Just another Test").
          text("test test test test").build();

  @Before
  public void initialization() {
    N1 = newsRepository.save(N1);
    N2 = newsRepository.save(N2);
  }

  @Test
  public void givenNewsSaved_whenFindNewsById_thenReturnNews() {
    News retN1 = newsRepository.findById(N1.getId()).orElseThrow(NotFoundException::new);
    assertThat(retN1).isEqualTo(N1);
  }

  @Test
  public void givenNewsSaved_whenFindNews_thenReturnAllNews() {
    List<News> news = new ArrayList<>();
    news.add(N1);
    news.add(N2);
    assertThat(newsRepository.findAll()).isEqualTo(news);
  }

  @Test(expected = NotFoundException.class)
  public void givenNewsSaved_whenFindUnknownNewsById_thenThrowNotFoundException() {
    newsRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
