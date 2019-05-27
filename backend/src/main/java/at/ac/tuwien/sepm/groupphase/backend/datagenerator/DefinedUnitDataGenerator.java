package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.implementation.SimpleEventService;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("generateData")
public class DefinedUnitDataGenerator implements DataGenerator<DefinedUnit> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventService.class);

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private final Set<Class<?>> dependencies =
      new HashSet<>(
          Arrays.asList(
              Event.class, Performance.class, PriceCategory.class, Hall.class, Unit.class));
  private PerformanceRepository performanceRepository;
  private PriceCategoryRepository priceCategoryRepository;
  private EventRepository eventRepository;
  private UnitRepository unitRepository;
  private DefinedUnitRepository definedUnitRepository;

  /** Create a new DefinedUnitDataGenerator. */
  @Autowired
  public DefinedUnitDataGenerator(
      PerformanceRepository performanceRepository,
      PriceCategoryRepository priceCategoryRepository,
      EventRepository eventRepository,
      UnitRepository unitRepository,
      DefinedUnitRepository definedUnitRepository) {
    this.performanceRepository = performanceRepository;
    this.priceCategoryRepository = priceCategoryRepository;
    this.eventRepository = eventRepository;
    this.unitRepository = unitRepository;
    this.definedUnitRepository = definedUnitRepository;
  }

  @Transactional
  @Override
  public void execute() {
    List<DefinedUnit> generatedDefinedUnits = new ArrayList<>();

    for (Event event : eventRepository.findAll()) {
      Hall hall = event.getHall();
      List<Unit> units = unitRepository.findAllByHall(hall);
      List<PriceCategory> priceCategories =
          priceCategoryRepository.findAllByEventOrderByPriceInCentsAsc(event);
      priceCategories.sort(Comparator.comparingInt(PriceCategory::getPriceInCents));

      int maxY = hall.getBoundaryPoint().getCoordinateY();
      int step = maxY / priceCategories.size();

      for (Performance performance :
          performanceRepository.findAllByEvent(event, Pageable.unpaged())) {
        generatedDefinedUnits.clear();

        int[] adherings = new int[hall.getBoundaryPoint().getCoordinateY()];
        IntStream.range(0, adherings.length).forEach(x -> adherings[x] = x);

        for (Unit unit : units) {
          if (unit.getLowerBoundary() != unit.getUpperBoundary()) {
            IntStream.range(
                    unit.getLowerBoundary().getCoordinateY() - 1,
                    unit.getUpperBoundary().getCoordinateY() - 1)
                .forEach(
                    x -> adherings[x] = adherings[unit.getLowerBoundary().getCoordinateY() - 1]);
          }
        }

        while (adherings[adherings.length - 1] >= priceCategories.size()) {

          normalizeArray(adherings);

          int toMerge2 = FAKER.random().nextInt(1, adherings[adherings.length - 1]);
          int toMerge1 = toMerge2 - 1;

          for (int k = 0; k < adherings.length; k++) {
            if (adherings[k] == toMerge1) {
              adherings[k] = toMerge2;
            }
          }

          normalizeArray(adherings);

          
        }

        for (int w = 0; w < adherings.length; w++) {
          LOGGER.info(Integer.toString(adherings[w]));
        }
        for (Unit unit : units) {
          int categoryIdx = adherings[unit.getLowerBoundary().getCoordinateY() - 1];
          PriceCategory category = priceCategories.get(categoryIdx);

          generatedDefinedUnits.add(
              new DefinedUnit.Builder()
                  .priceCategory(category)
                  .capacityFree(unit.getCapacity())
                  .performance(performance)
                  .unit(unit)
                  .build());
        }
        definedUnitRepository.saveAll(generatedDefinedUnits);
      }
    }
  }

  /** Javadoc. */
  public static void normalizeArray(int[] array) {
    int lastP = array[0];
    int pindex = 0;
    for (int k = 0; k < array.length; k++) {
      if (array[k] != lastP) {
        lastP = array[k];
        array[k] = ++pindex;
      } else {
        array[k] = pindex;
      }
    }
  }
  

  @Override
  public Class<DefinedUnit> getGeneratedType() {
    return DefinedUnit.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
