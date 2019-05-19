package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class PerformanceDataGenerator implements DataGenerator<Performance> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final int MIN_PERFORMANCES_PER_EVENT = 1;
  private static final int MAX_PERFORMANCES_PER_EVENT = 5;
  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(Event.class));
  private EventRepository eventRepository;
  private PerformanceRepository performanceRepository;

  @Autowired
  public PerformanceDataGenerator(
      EventRepository eventRepository, PerformanceRepository performanceRepository) {
    this.eventRepository = eventRepository;
    this.performanceRepository = performanceRepository;
  }

  @Override
  public void execute() {
    List<Performance> generatedPerformances = new ArrayList<>(MAX_PERFORMANCES_PER_EVENT);

    for (Event event : eventRepository.findAll()) {
      LocalDateTime initialStartAt =
          LocalDateTime.ofInstant(
              FAKER.date().future(365, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault());
      int numPerformances =
          FAKER.random().nextInt(MIN_PERFORMANCES_PER_EVENT, MAX_PERFORMANCES_PER_EVENT);

      generatedPerformances.clear();
      for (int i = 0; i < numPerformances; i++) {
        generatedPerformances.add(
            new Performance.Builder()
                .name(String.format("Aufführung %d", i + 1))
                .startAt(initialStartAt.plusDays(i))
                .event(event)
                .build());
      }
      performanceRepository.saveAll(generatedPerformances);
    }
  }

  @Override
  public Class<Performance> getGeneratedType() {
    return Performance.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
