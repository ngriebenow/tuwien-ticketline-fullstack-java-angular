package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("generateData")
public class NewsDataGenerator implements DataGenerator<News> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final int MAX_NEWS_COUNT = 25;
  private final Set<Class<?>> dependencies = new HashSet<>();
  private final NewsRepository newsRepository;

  @Autowired
  public NewsDataGenerator(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  @Transactional
  @Override
  public void execute() {
    List<News> generatedNews = new ArrayList<>(MAX_NEWS_COUNT);

    for (int i = 0; i < MAX_NEWS_COUNT; i++) {
      generatedNews.add(
          News.builder()
              .title(FAKER.lordOfTheRings().character()
                  + FAKER.hitchhikersGuideToTheGalaxy().character())
              .summary(FAKER.harryPotter().quote())
              .text(FAKER.lorem().paragraph(FAKER.number().numberBetween(8, 12)))
              .publishedAt(
                  LocalDateTime.ofInstant(
                      FAKER.date().past(365 * 3, TimeUnit.DAYS).toInstant(),
                      ZoneId.systemDefault()))
              .build());
    }
    newsRepository.saveAll(generatedNews);
  }

  @Override
  public Class<News> getGeneratedType() {
    return News.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
