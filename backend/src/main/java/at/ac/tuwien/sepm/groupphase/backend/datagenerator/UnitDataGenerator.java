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
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("generateData")
public class UnitDataGenerator implements DataGenerator<Unit> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(Hall.class));
  private UnitRepository unitRepository;
  private HallRepository hallRepository;

  private static final int SECTOR_MIN_SIZE_X = 2;
  private static final int SECTOR_MIN_SIZE_Y = 2;
  private static final int SECTOR_MAX_SIZE_X = 4;
  private static final int SECTOR_MAX_SIZE_Y = 4;

  private static final int SECTOR_CAPACITY_MIN = 5;
  private static final int SECTOR_CAPACITY_MAX = 20;

  @Autowired
  public UnitDataGenerator(UnitRepository unitRepository, HallRepository hallRepository) {
    this.unitRepository = unitRepository;
    this.hallRepository = hallRepository;
  }

  @Transactional
  @Override
  public void execute() {
    List<Unit> generatedUnits = new ArrayList<>();

    for (Hall hall : hallRepository.findAll()) {

      generatedUnits.clear();

      boolean[][] occupied = new boolean[
          hall.getBoundaryPoint().getCoordinateX()] [
              hall.getBoundaryPoint().getCoordinateY()];


      for (int i = 0; i < hall.getBoundaryPoint().getCoordinateY(); i++) {
        for (int j = 0; j < hall.getBoundaryPoint().getCoordinateX(); j++) {

          int sectorSizeX = FAKER.random().nextInt(SECTOR_MIN_SIZE_X,SECTOR_MAX_SIZE_X);
          int sectorSizeY = FAKER.random().nextInt(SECTOR_MIN_SIZE_Y,SECTOR_MAX_SIZE_Y);

          if (!occupied[i][j]) {

            boolean feasible = true;
            for (int k = i; k < i + sectorSizeX && feasible; k++) {
              for(int l = 0; l < j + sectorSizeY && feasible; l++) {
                if (k >= hall.getBoundaryPoint().getCoordinateX()
                    || l >= hall.getBoundaryPoint().getCoordinateY()
                    || occupied[k][l]) {
                  feasible = false;
                }
              }
            }

            if (feasible && FAKER.random().nextInt(-500,500) < -450) {

              for (int k = i; k < i + sectorSizeX; k++) {
                for(int l = 0; l < j + sectorSizeY; l++) {
                  occupied[k][l] = false;
                }
              }

              generatedUnits.add(
                  new Unit.Builder()
                      .name("Sektor " + FAKER.commerce().productName())
                      .lowerBoundary(new Point(j + 1, i + 1))
                      .upperBoundary(new Point(j + sectorSizeX, i + sectorSizeY))
                      .capacity(FAKER.random().nextInt(SECTOR_CAPACITY_MIN, SECTOR_CAPACITY_MAX))
                      .hall(hall)
                      .build());
            } else {
              generatedUnits.add(
                  new Unit.Builder()
                      .name(String.format("Reihe %d Sitz %d", j + 1, i + 1))
                      .lowerBoundary(new Point(j + 1, i + 1))
                      .upperBoundary(new Point(j + 1, i + 1))
                      .capacity(1)
                      .hall(hall)
                      .build());
            }
          }
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
