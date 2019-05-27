package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("generateData")
public class HallDataGenerator implements DataGenerator<Hall> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final int MAX_HALL_COUNT_PER_LOCATION = 10;
  private static final int MIN_WIDTH = 5;
  private static final int MAX_WIDTH = 25;
  private static final int MIN_ROWS = 5;
  private static final int MAX_ROWS = 25;
  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(Location.class));
  private HallRepository hallRepository;
  private LocationRepository locationRepository;

  @Autowired
  public HallDataGenerator(HallRepository hallRepository, LocationRepository locationRepository) {
    this.hallRepository = hallRepository;
    this.locationRepository = locationRepository;
  }

  @Transactional

  @Override
  public void execute() {
    List<Hall> generatedHalls = new ArrayList<>(MAX_HALL_COUNT_PER_LOCATION);

    for (Location location : locationRepository.findAll()) {
      for (int i = 0; i < FAKER.random().nextInt(MAX_HALL_COUNT_PER_LOCATION); i++) {
        Point boundary =
            new Point.Builder()
                .coordinateX(FAKER.random().nextInt(MIN_WIDTH, MAX_WIDTH))
                .coordinateY(FAKER.random().nextInt(MIN_ROWS, MAX_ROWS))
                .build();
        generatedHalls.add(
            new Hall.Builder()
                .version(1)
                .name(FAKER.pokemon().name() + " Saal")
                .boundaryPoint(boundary)
                .location(location)
                .build());
      }
      hallRepository.saveAll(generatedHalls);
    }
  }

  @Override
  public Class<Hall> getGeneratedType() {
    return Hall.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
