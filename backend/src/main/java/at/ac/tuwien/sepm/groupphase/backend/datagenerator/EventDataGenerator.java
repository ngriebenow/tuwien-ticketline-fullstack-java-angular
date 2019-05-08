package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import com.github.javafaker.Faker;
import java.time.Duration;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("generateData")
@Component
public class EventDataGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventDataGenerator.class);
  private static final int NUMBER_OF_EVENTS_TO_GENERATE = 25;

  private final EventRepository eventRepository;
  private final ArtistRepository artistRepository;
  private final HallRepository hallRepository;
  private final LocationRepository locationRepository;

  private final Faker faker;

  /** Generate demo event data. */
  public EventDataGenerator(
      EventRepository eventRepository,
      ArtistRepository artistRepository,
      HallRepository hallRepository,
      LocationRepository locationRepository) {
    this.eventRepository = eventRepository;
    this.artistRepository = artistRepository;
    this.hallRepository = hallRepository;
    this.locationRepository = locationRepository;

    faker = new Faker();
  }

  /** Generate event demo data. */
  @PostConstruct
  private void generateEvent() {
    if (eventRepository.count() > 0) {
      LOGGER.info("events already generated");
    } else {
      LOGGER.info("generating {} event entries", NUMBER_OF_EVENTS_TO_GENERATE);

      Location location =
          new Location.Builder()
              .place("Vienna")
              .postalCode("1160")
              .street("Resselgasse")
              .name("TU Bibliothek")
              .country("Austria")
              .build();

      Hall hall =
          new Hall.Builder()
              .boundaryPoint(new Point(1, 1))
              .location(location)
              .name("Vortragssaal")
              .version(1)
              .build();

      location = locationRepository.save(location);
      hall = hallRepository.save(hall);

      for (int i = 0; i < NUMBER_OF_EVENTS_TO_GENERATE; i++) {

        Artist artist = new Artist.Builder().name("Artist " + i).surname("Surname " + i).build();

        artistRepository.save(artist);

        Event event =
            new Event.Builder()
                .name(faker.lorem().characters(5, 10))
                .category(EventCategory.OTHER)
                .duration(Duration.ofHours(i))
                .content(faker.lorem().sentence(50))
                .artists(List.of(artist))
                .hall(hall)
                .build();
        LOGGER.debug("saving event {}", event);

        eventRepository.save(event);
      }
    }
  }
}
