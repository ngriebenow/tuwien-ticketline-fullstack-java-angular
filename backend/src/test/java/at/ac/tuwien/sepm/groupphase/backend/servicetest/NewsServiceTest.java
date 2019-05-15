package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class NewsServiceTest {

  @MockBean
  NewsRepository newsRepository;

  @Autowired private NewsService newsService;

  private News N1 = new News.Builder().
      id(1L).
      publishedAt(LocalDateTime.now()).
      title("Test").
      summary("Just one Test").
      text("abcdefghijk test news test news news").build();


  @Test
  public void givenNews_whenFindNewsById_thenReturnNews() throws NotFoundException {
    BDDMockito.given(newsRepository.findById(1L)).willReturn(Optional.of(N1));
    News retN1 = newsService.findOne(1L);
    assertThat(retN1).isEqualTo(N1);
  }

  @Test(expected = NotFoundException.class)
  public void givenNoNews_whenFindNewsById_thenThrowNotFoundException() throws NotFoundException {
    BDDMockito.given(newsRepository.findById(-1L)).willReturn(Optional.empty());
    newsService.findOne(-1L);
  }
}
