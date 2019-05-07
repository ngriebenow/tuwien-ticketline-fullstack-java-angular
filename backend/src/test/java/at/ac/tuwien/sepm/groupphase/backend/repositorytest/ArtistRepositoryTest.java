package at.ac.tuwien.sepm.groupphase.backend.repositorytest;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import java.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArtistRepositoryTest {

  @Autowired
  ArtistRepository artistRepository;

  private Artist A1 = new Artist(0L,"A1 name", "A1 surname",null);


  @Before
  public void initialization() {
    A1 = artistRepository.save(A1);
  }

  @Test
  public void givenArtistSaved_whenFindArtistById_thenReturnEvent() {
    Artist retA1 = artistRepository.findById(A1.getId()).orElseThrow(NotFoundException::new);
    assertThat(retA1,is(equalTo(A1)));
  }

  @Test(expected = NotFoundException.class)
  public void givenArtistSaved_whenFindUnknownArtistById_thenThrowNotFoundException() {
    artistRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }

}


