package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
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
public class EventRepositoryTest {

  @Autowired
  EventRepository eventRepository;

  private Event E1 =
      new Event.Builder()
          .name("Event1")
          .category(EventCategory.CINEMA)
          .duration(Duration.ofHours(2))
          .build();

  @Before
  public void initialization() {
    E1 = eventRepository.save(E1);
  }

  @Test
  public void givenEventSaved_whenFindEventById_thenReturnEvent() {
    Event retE1 = eventRepository.findById(E1.getId()).orElseThrow(NotFoundException::new);
    assertThat(retE1, is(equalTo(E1)));
  }

  @Test(expected = NotFoundException.class)
  public void givenEventSaved_whenFindUnknownEventById_thenThrowNotFoundException() {
    eventRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
