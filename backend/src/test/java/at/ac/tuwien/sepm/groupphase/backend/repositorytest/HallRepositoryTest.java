package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HallRepositoryTest {

  @Autowired HallRepository hallRepository;

  private Hall H1 = new Hall(0, 1, "Hall 1", new Point(), null);

  @Before
  public void initialization() {
    H1 = hallRepository.save(H1);
  }

  @Test
  public void givenHallSaved_whenFindHallById_thenReturnHall() {
    Hall retE1 = hallRepository.findById(H1.getId()).orElseThrow(NotFoundException::new);
    assertThat(retE1, is(equalTo(H1)));
  }

  @Test(expected = NotFoundException.class)
  public void givenHallSaved_whenFindUnknownHallById_thenThrowNotFoundException() {
    hallRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
