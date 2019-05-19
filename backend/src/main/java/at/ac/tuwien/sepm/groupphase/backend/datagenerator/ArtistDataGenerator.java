package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class ArtistDataGenerator implements DataGenerator<Artist> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final int MAX_ARTIST_COUNT = 21;
  private static Set<Class<?>> dependencies = new HashSet<>();
  private ArtistRepository artistRepository;

  @Autowired
  public ArtistDataGenerator(ArtistRepository artistRepository) {
    this.artistRepository = artistRepository;
  }

  @Override
  public void execute() {
    List<Artist> generatedArtists = new ArrayList<>(MAX_ARTIST_COUNT);
    for (int i = 0; i < MAX_ARTIST_COUNT; i++) {
      generatedArtists.add(
          new Artist.Builder()
              .name(FAKER.name().firstName())
              .surname(FAKER.name().lastName())
              .build());
    }
    artistRepository.saveAll(generatedArtists);
  }

  @Override
  public Class<Artist> getGeneratedType() {
    return Artist.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
