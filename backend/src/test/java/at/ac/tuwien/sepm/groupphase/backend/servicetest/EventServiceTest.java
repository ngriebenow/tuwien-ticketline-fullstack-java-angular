package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class EventServiceTest {

  @MockBean EventRepository eventRepository;

  @Autowired private EventService eventService;

  @Autowired private EventMapper eventMapper;

  private Event E1 =
      new Event.Builder()
          .name("Event1")
          .category(EventCategory.CINEMA)
          .duration(Duration.ofHours(2))
          .build();

  private Artist A1;
  private Hall H1;
  private Location L1;

  @Before
  public void initialize() {
    A1 = new Artist.Builder().id(0L).surname("Artist Surname 1").name("Artist Name 1").build();
    E1.setArtists(List.of(A1));

    L1 =
        new Location.Builder()
            .name("Loc 1")
            .street("Street 1")
            .postalCode("Post 1")
            .place("Place 1")
            .build();

    H1 =
        new Hall.Builder()
            .version(1)
            .name("Hall")
            .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
            .location(L1)
            .build();

    E1.setHall(H1);
  }

  @Test
  public void givenEvent_whenFindEventById_thenReturnEvent() {
    BDDMockito.given(eventRepository.findById(100L)).willReturn(Optional.of(E1));
    Event retE1 = eventMapper.eventDtoToEvent(eventService.getOneById(100L));
    Assert.assertThat(retE1, is(equalTo(E1)));
  }

  @Test(expected = NotFoundException.class)
  public void givenNoEvent_whenFindEventById_thenThrowNotFoundException() {
    BDDMockito.given(eventRepository.findById(-1L)).willThrow(new NotFoundException());
    eventService.getOneById(-1L);
  }
}