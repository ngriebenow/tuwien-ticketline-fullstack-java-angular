package at.ac.tuwien.sepm.groupphase.backend.serviceintegrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import java.time.Duration;
import java.time.LocalDateTime;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class EventServiceIntegrationTest {

  //@Autowired private EventRepository eventRepository;
  //@Autowired private PerformanceRepository performanceRepository;

  @Autowired private EventService eventService;

  @Autowired private PerformanceRepository performanceRepository;
  @Autowired private HallRepository hallRepository;
  @Autowired private LocationRepository locationRepository;
  @Autowired private ArtistRepository artistRepository;
  @Autowired private EventRepository eventRepository;

  @Autowired private EventMapper eventMapper;



  private Event E1;
  private Artist A1;
  private Hall H1;
  private Location L1;
  private Performance P1;
  private Performance P2;

  @Before
  public void initialize() {
    E1 = new Event.Builder()
        .name("Event1")
        .category(EventCategory.CINEMA)
        .duration(Duration.ofHours(2))
        .content("Content")
        .build();


    A1 = new Artist.Builder().id(0L).surname("Artist Surname 1").name("Artist Name 1").build();
    A1 = artistRepository.save(A1);

    E1.setArtists(List.of(A1));

    L1 =
        new Location.Builder()
            .name("Loc 1")
            .street("Street 1")
            .postalCode("Post 1")
            .place("Place 1")
            .country("Austria")
            .build();
    locationRepository.save(L1);

    H1 =
        new Hall.Builder()
            .version(1)
            .name("Hall")
            .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
            .location(L1)
            .build();
    hallRepository.save(H1);

    E1.setHall(H1);

    eventRepository.save(E1);

    P1 = new Performance.Builder()
        .name("Perf 1")
        .startAt(LocalDateTime.now())
        .id(0L)
        .event(E1)
        .build();
    performanceRepository.save(P1);

    P2 = new Performance.Builder()
        .name("Perf 2")
        .startAt(LocalDateTime.now())
        .id(1L)
        .event(E1)
        .build();
    performanceRepository.save(P2);


  }

  @Test
  public void givenEvent_whenFindByEvent_thenReturnEvent() {
    Event retE1 = eventMapper.eventDtoToEvent(eventService.getOneById(E1.getId()));
    Assert.assertThat(retE1, is(equalTo(E1)));
  }

  @Test(expected = NotFoundException.class)
  public void givenNoEvent_whenFindEventById_thenThrowNotFoundException() {
    eventService.getOneById(-1L);
  }




  @Test
  public void givenEventId_whenFindPerformancesByEventId_thenReturnPerformances() {

  }
}

