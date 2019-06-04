package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PerformanceRepositoryTest {

  @Autowired
  PerformanceRepository performanceRepository;
  @Autowired
  EventRepository eventRepository;

  private Event E1 =
      new Event.Builder()
          .name("Event1")
          .category(EventCategory.CINEMA)
          .duration(Duration.ofHours(2))
          .build();

  private Performance P1 =
      new Performance.Builder()
          .name("Perf 1")
          .startAt(ZonedDateTime.now())
          .id(0L)
          .event(E1)
          .build();

  private Performance P2 =
      new Performance.Builder()
          .name("Perf 2")
          .startAt(ZonedDateTime.now())
          .id(1L)
          .event(E1)
          .build();

  @Before
  public void initialization() {
    E1 = eventRepository.save(E1);
    P1 = performanceRepository.save(P1);
    P2 = performanceRepository.save(P2);
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

  public void givenEventId_whenFindPerformancesByEvent_thenReturnPerformances() {
    List<Performance> perfList1 = performanceRepository.findAllByEvent(E1, PageRequest.of(1, 1));
    List<Performance> perfList2 = performanceRepository.findAllByEvent(E1, PageRequest.of(1, 1));

    perfList1.addAll(perfList2);
    assertThat(perfList1.contains(P1), is(true));
    assertThat(perfList1.contains(P2), is(true));
  }
}
