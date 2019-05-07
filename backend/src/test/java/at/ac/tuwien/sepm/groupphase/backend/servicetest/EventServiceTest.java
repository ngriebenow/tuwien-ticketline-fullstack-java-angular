package at.ac.tuwien.sepm.groupphase.backend.servicetest;

import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class EventServiceTest {

  @Autowired
  EventService eventService;

  public EventServiceTest() {
  }

  @Test
  public void test()
  {
    Assert.fail();
  }

}