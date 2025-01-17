package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import java.util.ArrayList;
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
public class LocationDataGenerator implements DataGenerator<Location> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final int MAX_LOCATION_COUNT = 10;
  private final Set<Class<?>> dependencies = new HashSet<>();
  private LocationRepository locationRepository;

  @Autowired
  public LocationDataGenerator(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  @Transactional
  @Override
  public void execute() {
    List<Location> generatedLocations = new ArrayList<>(MAX_LOCATION_COUNT);

    for (int i = 0; i < MAX_LOCATION_COUNT; i++) {
      Address address = FAKER.address();
      if (i % 2 == 0) {
        generatedLocations.add(
            new Location.Builder()
                .name(FAKER.harryPotter().location())
                .country("Austria")
                .place(address.city())
                .postalCode(address.zipCode())
                .street(address.streetAddress())
                .build());
      }
      generatedLocations.add(
          new Location.Builder()
              .name(FAKER.harryPotter().location())
              .country(FAKER.address().country())
              .place(address.city())
              .postalCode(address.zipCode())
              .street(address.streetAddress())
              .build());
    }
    locationRepository.saveAll(generatedLocations);
  }

  @Override
  public Class<Location> getGeneratedType() {
    return Location.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
