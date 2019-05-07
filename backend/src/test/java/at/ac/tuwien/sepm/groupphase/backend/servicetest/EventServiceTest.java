package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTest {

  @MockBean EventRepository eventRepository;

  @Autowired
  private EventService eventService;

  @Autowired
  private EventMapper eventMapper;

  private Event E1 =
      new Event(0, "Event 1", EventCategory.CINEMA, "Content 1", Duration.ofHours(2), null, null);

  private Artist A1;
  private Hall H1;

  @Before
  public void initialize() {
    A1 = new Artist(0, "Artist Surname 1", "Artist Name 1", List.of(E1));
    E1.setArtists(List.of(A1));

    H1 = new Hall(0, 1, "Hall 1", new Point(1, 1), null);
    E1.setHall(H1);
  }

  @Test
  public void findAllMessageAsUser() {
    BDDMockito.given(eventRepository.findById(1L)).willReturn(Optional.of(E1));
    Event retE1 = eventMapper.eventDtoToEvent(eventService.getOneById(1L));
    Assert.assertThat(retE1, is(equalTo(E1)));
  }
}
