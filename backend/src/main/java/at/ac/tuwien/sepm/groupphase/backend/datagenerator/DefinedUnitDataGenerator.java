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
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class DefinedUnitDataGenerator implements DataGenerator<DefinedUnit> {

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

  @Override
  public void execute() {
    List<DefinedUnit> generatedDefinedUnits = new ArrayList<>();

    for (Event event : eventRepository.findAll()) {
      Hall hall = event.getHall();
      List<Unit> units = unitRepository.findAllByHall(hall);
      List<PriceCategory> priceCategories = priceCategoryRepository.findAllByEvent(event);
      priceCategories.sort(Comparator.comparingInt(PriceCategory::getPriceInCents));

      int maxY = hall.getBoundaryPoint().getCoordinateY();
      int step = maxY / priceCategories.size();

      for (Performance performance :
          performanceRepository.findAllByEvent(event, Pageable.unpaged())) {
        generatedDefinedUnits.clear();
        for (Unit unit : units) {
          int categoryIdx = unit.getLowerBoundary().getCoordinateY() / step;
          PriceCategory category =
              priceCategories.get(Math.min(priceCategories.size() - 1, categoryIdx));

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

  @Override
  public Class<DefinedUnit> getGeneratedType() {
    return DefinedUnit.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
