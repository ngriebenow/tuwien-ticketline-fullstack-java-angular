package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
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

    eventRepository.save(e);

    Event e2 = new Event();
    e2.setName("n2");
    e2.setContent("cool");
    e2.setId(1L);

    eventRepository.save(e2);


    List<Event> l = eventRepository.findAll();

    System.out.println(l);
    }

}


