package at.ac.tuwien.sepm.groupphase.backend.repositorytest;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTest {

  @Autowired
  LocationRepository locationRepository;

  private Location L1 = new Location("Location 1","Street 1", "1000","Place 1", "Austria");


  @Before
  public void initialization() {
    L1 = locationRepository.save(L1);
  }

  @Test
  public void givenLocationSaved_whenFindLocationById_thenReturnEvent() {
    Location retL1 = locationRepository.findById(L1.getId()).orElseThrow(NotFoundException::new);
    assertThat(retL1,is(equalTo(L1)));
  }

  @Test(expected = NotFoundException.class)
  public void givenLocationSaved_whenFindUnknownLocationById_thenThrowNotFoundException() {
    locationRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }

}


