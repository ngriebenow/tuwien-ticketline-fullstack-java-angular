package at.ac.tuwien.sepm.groupphase.backend.serviceintegrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
import java.util.ArrayList;
import java.util.List;
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
public class NewsAndPictureIntegrationTest {

  @Autowired private NewsService newsService;
  @Autowired private PictureService pictureService;
  @Autowired private PictureRepository pictureRepository;
  @Autowired private NewsMapper newsMapper;

  private DetailedNewsDto N1;
  private DetailedNewsDto N2;

  private List<Long> pictureIds1 = new ArrayList<>();
  private List<Long> pictureIds2 = new ArrayList<>();

  @Before
  public void initialization() {

    pictureIds1.add(0,4L);
    pictureIds1.add(1,5L);

    N1 = DetailedNewsDto.builder()
        .id(1L)
        .pictureIds(pictureIds1)
        .title("Test")
        .summary("Just one Test")
        .text("abcdefghijk test news test news news").build();

  }

  @Test
  public void givenNoNews_whenCreatePicturesThenNewsThenUpdatePictures_thenReturnPicturesByNews() {

    PictureDto P1 = new PictureDto();
    P1.setData(new byte[1]);

    PictureDto P2 = new PictureDto();
    P2.setData(new byte[1]);

    pictureIds2.add(0, pictureService.create(P1));
    pictureIds2.add(1, pictureService.create(P2));

    N2 = DetailedNewsDto.builder()
        .id(1L)
        .pictureIds(pictureIds2)
        .title("Test")
        .summary("Just one Test")
        .text("abcdefghijk test news test news news").build();

    N2 = newsService.create(N2);

    pictureService.updateSetNews(N2, pictureIds2);

    List<Picture> pictures = pictureRepository
        .findAllByNewsOrderByIdAsc(newsMapper.detailedNewsDtoToNews(N2));

    List<Long> retPictureIds = new ArrayList<>();

    pictures.forEach(p -> retPictureIds.add(p.getId()));

    assertThat(retPictureIds).isEqualTo(pictureIds2);

  }

  @Test(expected = ValidationException.class)
  public void givenNoNews_whenSetNewsInPictures_thenThrowValidationException() {
    pictureService.updateSetNews(N1,N1.getPictureIds());
  }


}

