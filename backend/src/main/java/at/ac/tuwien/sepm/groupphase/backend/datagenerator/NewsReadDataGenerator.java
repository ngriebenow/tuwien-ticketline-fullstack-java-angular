package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsRead;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsReadRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
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
public class NewsReadDataGenerator implements DataGenerator<NewsRead> {

  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(User.class, News.class));
  private static final Faker FAKER = new Faker(new Locale("de-at"));

  private static final int MAX_READ_COUNT_PER_USER = 15;

  private UserRepository userRepository;
  private NewsRepository newsRepository;
  private NewsReadRepository newsReadRepository;

  /**
   * Construct the entity.
   */
  @Autowired
  public NewsReadDataGenerator(UserRepository userRepository,
      NewsRepository newsRepository,
      NewsReadRepository newsReadRepository) {
    this.userRepository = userRepository;
    this.newsReadRepository = newsReadRepository;
    this.newsRepository = newsRepository;
  }

  @Transactional
  @Override
  public void execute() {
    List<NewsRead> generatedNewsReads = new ArrayList<>(MAX_READ_COUNT_PER_USER);

    for (User user : userRepository.findAll()) {
      for (long i = 0; i < FAKER.random().nextInt(MAX_READ_COUNT_PER_USER); i++) {
        try {
          News news = newsRepository.findById(i).orElseThrow(NotFoundException::new);
          NewsRead newsRead = new NewsRead.Builder()
              .news(news)
              .user(user)
              .build();
          generatedNewsReads.add(newsRead);

        } catch (NotFoundException e) {
          //not critical so just continue
          continue;
        }

      }
      newsReadRepository.saveAll(generatedNewsReads);
    }
  }

  @Override
  public Class<NewsRead> getGeneratedType() {
    return NewsRead.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
