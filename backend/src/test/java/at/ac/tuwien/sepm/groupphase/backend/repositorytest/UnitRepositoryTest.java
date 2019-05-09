package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitRepositoryTest {

  private static Unit UNIT_1;
  private static final String NAME_1 = "Unit 1";
  private static final Point LOWER_BOUNDARY_1 = new Point();
  private static final Point UPPER_BOUNDARY_1 = new Point();
  private static final int CAPACITY_1 = 24;

  @Autowired UnitRepository unitRepository;

  @BeforeClass
  public static void setUpOnce() {
    UNIT_1 =
        new Unit.Builder()
            .name(NAME_1)
            .lowerBoundary(LOWER_BOUNDARY_1)
            .upperBoundary(UPPER_BOUNDARY_1)
            .capacity(CAPACITY_1)
            .build();
  }

  @Before
  public void setUp() {
    unitRepository.save(UNIT_1);
  }

  @Test
  public void givenUnitSaved_whenFindById_thenReturnHall() {
    Unit unit = unitRepository.findById(UNIT_1.getId()).orElseThrow(NotFoundException::new);
    assertThat(unit).isEqualTo(UNIT_1);
  }

  public void givenUnitSaved_whenFindByUnknownId_thenNotFoundException() {
    assertThatThrownBy(() -> unitRepository.findById(-1L).orElseThrow(NotFoundException::new))
        .isEqualTo(NotFoundException.class);
  }
}
