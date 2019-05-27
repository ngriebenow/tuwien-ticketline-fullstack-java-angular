package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.picture.PictureMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
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
public class PictureServiceTest {

  @MockBean
  PictureRepository pictureRepository;

  @Autowired
  private PictureService pictureService;
  @Autowired
  private PictureMapper pictureMapper;

  private Picture P1 = new Picture.Builder().
      id(1L).build();


  @Test
  public void givenPicture_whenFindPictureById_thenReturnPicture() throws NotFoundException {
    BDDMockito.given(pictureRepository.findById(1L)).willReturn(Optional.of(P1));
    Picture retP1 = pictureMapper.pictureDtoToPicture(pictureService.findOne(1L));
    assertThat(retP1).isEqualTo(P1);
  }

  @Test(expected = NotFoundException.class)
  public void givenNoPictures_whenFindPicturesById_thenThrowNotFoundException()
      throws NotFoundException {
    BDDMockito.given(pictureRepository.findById(-1L)).willReturn(Optional.empty());
    pictureService.findOne(-1L);
  }
}
