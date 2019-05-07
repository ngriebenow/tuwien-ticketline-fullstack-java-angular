package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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


  @Test
  public void test() {
    Event e = new Event();

    e.setName("name");
    e.setContent("content");
    e.setDuration(Duration.ofHours(2));
    e.setCategory(EventCategory.CINEMA);

    List<Artist> artists = new ArrayList<>();
    Artist a = new Artist();
    a.setName("a1");
    a.setSurname("a2");
    artists.add(a);
    e.setArtists(artists);

    eventRepository.save(e);

    Event e2 = new Event();

    e2.setName("name");
    e2.setContent("content");
    e2.setDuration(Duration.ofHours(4));
    e2.setCategory(EventCategory.CINEMA);

    eventRepository.save(e2);

    List<Event> l = eventRepository.findAll();

    System.out.println(l);
    }

}


