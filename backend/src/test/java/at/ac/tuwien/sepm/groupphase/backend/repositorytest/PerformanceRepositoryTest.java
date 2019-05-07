package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PerformanceRepositoryTest {

  @Autowired PerformanceRepository performanceRepository;

  private Performance P1 = new Performance(0, LocalDateTime.now(), "Performance 1", null);

  @Before
  public void initialization() {
    P1 = performanceRepository.save(P1);
  }

  @Test
  public void givenPerformanceSaved_whenFindPerformanceById_thenReturnEvent() {
    Performance retP1 =
        performanceRepository.findById(P1.getId()).orElseThrow(NotFoundException::new);
    assertThat(retP1, is(equalTo(P1)));
  }

  @Test(expected = NotFoundException.class)
  public void givenPerformanceSaved_whenFindUnknownPerformanceById_thenThrowNotFoundException() {
    performanceRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
