package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import com.github.javafaker.Faker;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class PictureDataGenerator implements DataGenerator<Picture> {

  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(News.class));
  private static final Faker FAKER = new Faker(new Locale("de-at"));

  private static final int MAX_PICTURE_COUNT_PER_NEWS = 5;
  private static final int PICTURE_HEIGHT = 64;
  private static final int PICTURE_WIDTH = 64;

  private PictureRepository pictureRepository;
  private NewsRepository newsRepository;

  @Autowired
  public PictureDataGenerator(PictureRepository pictureRepository, NewsRepository newsRepository) {
    this.pictureRepository = pictureRepository;
    this.newsRepository = newsRepository;
  }

  @Override
  public void execute() {
    List<Picture> generatedPictures = new ArrayList<>(MAX_PICTURE_COUNT_PER_NEWS);

    int iteration = 1;
    for (News news : newsRepository.findAll()) {
      for (int i = 0; i < FAKER.random().nextInt(MAX_PICTURE_COUNT_PER_NEWS); i++) {
        Picture picture =
            new Picture.Builder()
                .data(generateData(iteration++))
                .news(news)
                .build();
        generatedPictures.add(picture);
      }
      pictureRepository.saveAll(generatedPictures);
    }
  }
  /**
   * Used to create dummy pictures.
   * Return a byte array containing a picture with grey background
   * and the String Picture plus iteration number.
   *
   * @param iteration appears on the picture so that pictures can be distinguished.
   * @return a byte array containing the picture.
   */

  private byte [] generateData(int iteration) {
    BufferedImage image =
        new BufferedImage(PICTURE_WIDTH, PICTURE_HEIGHT,
            BufferedImage.TYPE_INT_ARGB);

    Graphics2D graphics = image.createGraphics();
    graphics.setPaint(Color.decode("#C4C4C4"));
    graphics.fill(new Rectangle(0,0, PICTURE_WIDTH, PICTURE_HEIGHT));
    graphics.setPaint(Color.BLACK);
    graphics.drawString("Picture " + iteration, 0,PICTURE_HEIGHT / 2);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "png", outputStream);
    } catch (IOException e) {
      System.err.println("Error occured while creating test image " + e.getMessage());
    }

    return outputStream.toByteArray();
  }

  @Override
  public Class<Picture> getGeneratedType() {
    return Picture.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
