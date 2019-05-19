package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DefinedUnitRepositoryTest {

  private DefinedUnit DEFINED_UNIT_1 = new DefinedUnit.Builder().capacityFree(24).build();

  @Autowired DefinedUnitRepository definedUnitRepository;

  @Before
  public void setUp() {
    definedUnitRepository.save(DEFINED_UNIT_1);
  }

  @Test
  public void givenDefinedUnitSaved_whenFindDefinedUnitById_thenReturnDefinedUnit() {
    DefinedUnit definedUnit = definedUnitRepository.findById(DEFINED_UNIT_1.getId()).get();
    assertThat(definedUnit).isEqualTo(DEFINED_UNIT_1);
  }

  @Test(expected = NotFoundException.class)
  public void givenDefinedUnitSaved_whenFindUnknownDefinedUnitById_thenThrowNotFoundException() {
    definedUnitRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
