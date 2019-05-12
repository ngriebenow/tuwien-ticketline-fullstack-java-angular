package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
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

@Component
@Profile("generateData")
public class UnitDataGenerator implements DataGenerator<Unit> {

  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(Hall.class));
  private static final Faker FAKER = new Faker(new Locale("de-at"));

  private UnitRepository unitRepository;
  private HallRepository hallRepository;

  @Autowired
  public UnitDataGenerator(UnitRepository unitRepository, HallRepository hallRepository) {
    this.unitRepository = unitRepository;
    this.hallRepository = hallRepository;
  }

  @Override
  public void execute() {
    List<Unit> generatedUnits = new ArrayList<>();

    for (Hall hall : hallRepository.findAll()) {

      generatedUnits.clear();
      for (int i = 0; i < hall.getBoundaryPoint().getCoordinateY(); i++) {
        for (int j = 0; j < hall.getBoundaryPoint().getCoordinateX(); j++) {
          generatedUnits.add(
              new Unit.Builder()
                  .name(String.format("Unit %d %d", j, i))
                  .lowerBoundary(new Point(j, i))
                  .upperBoundary(new Point(j, i))
                  .capacity(1)
                  .hall(hall)
                  .build());
        }
      }
      unitRepository.saveAll(generatedUnits);
    }
  }

  @Override
  public Class<Unit> getGeneratedType() {
    return Unit.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}