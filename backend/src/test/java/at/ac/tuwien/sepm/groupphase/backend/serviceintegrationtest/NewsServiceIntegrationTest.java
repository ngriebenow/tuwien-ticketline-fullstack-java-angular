package at.ac.tuwien.sepm.groupphase.backend.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class NewsServiceIntegrationTest {

  @Autowired private NewsService newsService;
  @Autowired private UserRepository userRepository;

  private User U1;
  private User U2;

  private DetailedNewsDto N1 = DetailedNewsDto.builder()
      .id(1L)
      .title("Test")
      .summary("Just one Test")
      .text("abcdefghijk test news test news news").build();

  private List<Long> pictureIds = new ArrayList<>();

  private DetailedNewsDto unknownPictureIdNews;

  private DetailedNewsDto emptyTitleNews = DetailedNewsDto.builder()
      .id(2L)
      .summary("Just one Test")
      .text("abcdefghijk test news test news news").build();

  private DetailedNewsDto emptyTextNews = DetailedNewsDto.builder()
      .id(2L)
      .title("Test")
      .summary("Just one Test").build();

  private DetailedNewsDto emptySummaryNews = DetailedNewsDto.builder()
      .id(1L)
      .title("Test")
      .text("abcdefghijk test news test news news").build();

  @Before
  public void initialization() {

    pictureIds.add(0,-1L);
    unknownPictureIdNews = DetailedNewsDto.builder()
        .id(1L)
        .pictureIds(pictureIds)
        .title("Test")
        .summary("Just one Test")
        .text("abcdefghijk test news test news news").build();

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

  @Test
  //User reads the single news entry and zero unread news entries are left for this user
  public void givenUserAndNoNews_whenCreateNewsThenFindOneThenFindNew_thenReturnNoNews() {
    N1 = newsService.create(N1);
    DetailedNewsDto retN1 = newsService.findOne(N1.getId(), U1);
    List<SimpleNewsDto> simpleNewsDtos = newsService.findAllNew(U1, Pageable.unpaged());
    assertThat(simpleNewsDtos.size()).isEqualTo(0);
  }

  @Test
  //User reads the single news entry and find all still return one element
  public void givenUserAndNoNews_whenCreateNewsThenFindOneThenFindAll_thenReturnNews() {
    N1 = newsService.create(N1);
    DetailedNewsDto retN1 = newsService.findOne(N1.getId(), U1);
    List<SimpleNewsDto> simpleNewsDtos = newsService.findAll(U1, Pageable.unpaged());
    assertThat(simpleNewsDtos.size()).isEqualTo(1);
  }

  //User reads the single news entry and for another user news is still considered as new
  @Test
  public void givenUserAndNoNews_whenCreateNewsThenFindOneThenFindNewByOther_thenReturnNews() {
    N1 = newsService.create(N1);
    DetailedNewsDto retN1 = newsService.findOne(N1.getId(), U1);
    List<SimpleNewsDto> simpleNewsDtos = newsService.findAllNew(U2, Pageable.unpaged());
    assertThat(simpleNewsDtos.size()).isEqualTo(1);
  }

  @Test(expected = ValidationException.class)
  public void givenNoNews_whenCreateNewsWithEmptyTitle_thenThrowValidationException() {
    DetailedNewsDto retN1 = newsService.create(emptyTitleNews);
  }

  @Test(expected = ValidationException.class)
  public void givenNoNews_whenCreateNewsWithEmptySummary_thenThrowValidationException() {
    DetailedNewsDto retN1 = newsService.create(emptySummaryNews);
  }

  @Test(expected = ValidationException.class)
  public void givenNoNews_whenCreateNewsWithEmptyText_thenThrowValidationException() {
    DetailedNewsDto retN1 = newsService.create(emptyTextNews);
  }

  @Test(expected = ValidationException.class)
  public void givenNoNews_whenCreateNewsWithUnknownPictureIds_thenThrowValidationException() {
    DetailedNewsDto retN1 = newsService.create(unknownPictureIdNews);
  }

  @Test(expected = NotFoundException.class)
  public void givenNoNews_whenFindNewsById_thenThrowNotFoundException() {
    DetailedNewsDto retN1 = newsService.findOne(-1L, U1);
  }
}
