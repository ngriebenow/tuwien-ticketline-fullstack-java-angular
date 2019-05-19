package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import com.github.javafaker.Faker;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class EventDataGenerator implements DataGenerator<Event> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final int MAX_EVENT_COUNT = 25;
  private static final int MIN_ARTISTS = 1;
  private static final int MAX_ARTISTS = 4;
  private static final int MIN_EVENT_DURATION = 1;
  private static final int MAX_EVENT_DURATION = 3;
  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(Artist.class, Hall.class));
  private EventRepository eventRepository;
  private ArtistRepository artistRepository;
  private HallRepository hallRepository;

  /** Create a new EventDataGenerator. */
  @Autowired
  public EventDataGenerator(
      EventRepository eventRepository,
      ArtistRepository artistRepository,
      HallRepository hallRepository) {
    this.eventRepository = eventRepository;
    this.artistRepository = artistRepository;
    this.hallRepository = hallRepository;
  }

  @Override
  public void execute() {
    List<Event> generatedEvents = new ArrayList<>(MAX_EVENT_COUNT);

    List<Hall> halls = hallRepository.findAll();
    List<Artist> artists = artistRepository.findAll();
    List<EventCategory> categories = Arrays.asList(EventCategory.values());

    for (int i = 0; i < MAX_EVENT_COUNT; i++) {

      List<Artist> participatingArtists = new ArrayList<>();
      for (int j = 0; j < FAKER.random().nextInt(MIN_ARTISTS, MAX_ARTISTS); j++) {
        Artist artist = artists.get(FAKER.random().nextInt(artists.size()));
        if (!participatingArtists.contains(artist)) {
          participatingArtists.add(artist);
        }
      }

      generatedEvents.add(
          new Event.Builder()
              .name(FAKER.lorem().characters(5, 10))
              .category(categories.get(FAKER.random().nextInt(categories.size())))
              .duration(
                  Duration.ofHours(FAKER.random().nextInt(MIN_EVENT_DURATION, MAX_EVENT_DURATION)))
              .content(FAKER.lorem().characters(20, 255))
              .artists(participatingArtists)
              .hall(halls.get(FAKER.random().nextInt(halls.size())))
              .build());
    }
    eventRepository.saveAll(generatedEvents);
  }

  @Override
  public Class<Event> getGeneratedType() {
    return Event.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
