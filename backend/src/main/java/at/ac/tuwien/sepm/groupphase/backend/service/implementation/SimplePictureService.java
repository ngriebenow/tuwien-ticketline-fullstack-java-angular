package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.picture.PictureMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimplePictureService implements PictureService {

  private final PictureRepository pictureRepository;
  private final PictureMapper pictureMapper;
  private final NewsMapper newsMapper;
  private final NewsRepository newsRepository;
  private final EntityManager entityManager;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SimplePictureService.class);

  /**
   * picture service constructor.
   *
   * @param pictureRepository to get pictures belonging to news entry
   * @param pictureMapper to map between dto and entity
   * @param newsMapper to map between dto and entity
   * @param newsRepository to check for referential integrity
   * @param entityManager for the update query
   */

  public SimplePictureService(PictureRepository pictureRepository,
      PictureMapper pictureMapper, NewsMapper newsMapper,
      NewsRepository newsRepository, EntityManager entityManager) {
    this.pictureRepository = pictureRepository;
    this.pictureMapper = pictureMapper;
    this.newsMapper = newsMapper;
    this.newsRepository = newsRepository;
    this.entityManager = entityManager;
  }

  @Transactional
  @Override
  public PictureDto findOne(Long id) throws NotFoundException {
    LOGGER.info("get picture by id");
    return pictureMapper
        .pictureToPictureDto(pictureRepository.findById(id).orElseThrow(
            () -> {
              String msg = "Can't find picture with id " + id;
              LOGGER.error(msg);
              return new NotFoundException(msg);
            }));
  }

  @Transactional
  @Override
  public Long create(PictureDto pictureDto) {
    LOGGER.info("create picture");
    if (pictureDto.getData() == null) {
      String msg = "Error while creating picture, Data must not be empty.";
      LOGGER.error(msg);
      throw new ValidationException(msg);
    }
    Picture picture = pictureRepository.save(pictureMapper.pictureDtoToPicture(pictureDto));
    return picture.getId();
  }

  @Transactional
  @Override
  public void updateSetNews(DetailedNewsDto newsDto, List<Long> pictureIds) {

    LOGGER.info("set news for pictures");
    if (!newsRepository.existsById(newsDto.getId())) {
      String msg = "Error while setting foreign key news in picture, news with id "
          + newsDto.getId() + " does not exists";
      LOGGER.error(msg);
      throw new ValidationException(msg);
    }

    String queryString = "UPDATE Picture SET news = :news WHERE id = :id";

    Query q = entityManager.createQuery(queryString);

    for (Long id : pictureIds) {
      if (!pictureRepository.existsById(id)) {
        String msg = "Error while setting foreign key news in picture, picture with id "
            + id + " does not exists";
        LOGGER.error(msg);
        throw new ValidationException(msg);
      }
      q.setParameter("news", newsMapper.detailedNewsDtoToNews(newsDto));
      q.setParameter("id", id);
      q.executeUpdate();

    }
  }

}

