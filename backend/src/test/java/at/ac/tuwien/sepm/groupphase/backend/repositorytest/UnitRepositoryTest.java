package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitRepositoryTest {

  @Autowired
  UnitRepository unitRepository;
  private Unit UNIT_1 =
      new Unit.Builder()
          .name("Unit 1")
          .capacity(24)
          .lowerBoundary(new Point(0, 0))
          .upperBoundary(new Point(1, 1))
          .build();

  @Before
  public void setUp() {
    unitRepository.save(UNIT_1);
  }

  @Test
  public void givenUnitSaved_whenFindUnitById_thenReturnUnit() {
    Unit unit = unitRepository.findById(UNIT_1.getId()).get();
    assertThat(unit).isEqualTo(UNIT_1);
  }

  @Test(expected = NotFoundException.class)
  public void givenUnitSaved_whenFindUnknownUnitById_thenThrowNotFoundException() {
    unitRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
