package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import com.github.javafaker.Faker;
import java.awt.Color;
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
public class PriceCategoryDataGenerator implements DataGenerator<PriceCategory> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final int MIN_PRICE_CATEGORIES_PER_EVENT = 1;
  private static final int MAX_PRICE_CATEGORIES_PER_EVENT = 5;
  private static final int MIN_BASE_PRICE = 1500;
  private static final int MAX_BASE_PRICE = 9000;
  private static final double BASE_PRICE_DECREASE_FACTOR = 0.1;
  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(Event.class));
  private EventRepository eventRepository;
  private PriceCategoryRepository priceCategoryRepository;

  @Autowired
  public PriceCategoryDataGenerator(
      EventRepository eventRepository, PriceCategoryRepository priceCategoryRepository) {
    this.eventRepository = eventRepository;
    this.priceCategoryRepository = priceCategoryRepository;
  }

  @Transactional
  @Override
  public void execute() {
    List<PriceCategory> generatedPriceCategories = new ArrayList<>(MAX_PRICE_CATEGORIES_PER_EVENT);

    for (Event event : eventRepository.findAll()) {
      int basePrice = FAKER.random().nextInt(MIN_BASE_PRICE, MAX_BASE_PRICE);

      generatedPriceCategories.clear();

      int pcCount = FAKER.random().nextInt(
          MIN_PRICE_CATEGORIES_PER_EVENT,MAX_PRICE_CATEGORIES_PER_EVENT);

      for (int i = 0; i < pcCount; i++) {
        Color color =
            new Color(
                FAKER.random().nextInt(256),
                FAKER.random().nextInt(256),
                FAKER.random().nextInt(256));
        generatedPriceCategories.add(
            new PriceCategory.Builder()
                .name(String.format("Kategorie %d", i + 1))
                .color(color)
                .priceInCents((int) (basePrice - i * BASE_PRICE_DECREASE_FACTOR * basePrice))
                .event(event)
                .build());
      }
      priceCategoryRepository.saveAll(generatedPriceCategories);
    }
  }

  @Override
  public Class<PriceCategory> getGeneratedType() {
    return PriceCategory.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
