package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PictureRepositoryTest {

  @Autowired
  PictureRepository pictureRepository;

  private Picture P1;

  @Before
  public void initialization() {

    P1 = new Picture.Builder().
        id(0L).
        build();
    P1 = pictureRepository.save(P1);
  }

  @Test
  public void givenPictureSaved_whenFindPictureById_thenReturnPicture() {
    Picture retP1 = pictureRepository.findById(P1.getId()).orElseThrow(NotFoundException::new);
    assertThat(retP1).isEqualTo(P1);
  }


  @Test(expected = NotFoundException.class)
  public void givenPictureSaved_whenFindUnknownPictureById_thenThrowNotFoundException() {
    pictureRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }

}
