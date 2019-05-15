package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.picture.PictureMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimplePictureService implements PictureService {

  private final PictureRepository pictureRepository;
  private final PictureMapper pictureMapper;
  private final NewsMapper newsMapper;
  private final EntityManager entityManager;
  /**
   * picture service constructor.
   *
   * @param pictureRepository to get pictures belonging to news entry
   * @param pictureMapper to map between dto and entity
   * @param newsMapper to map between dto and entity
   * @param entityManager for the update query
   */

  public SimplePictureService(PictureRepository pictureRepository,
      PictureMapper pictureMapper, NewsMapper newsMapper, EntityManager entityManager) {
    this.pictureRepository = pictureRepository;
    this.pictureMapper = pictureMapper;
    this.newsMapper = newsMapper;
    this.entityManager = entityManager;
  }

  @Override
  public PictureDto findOne(Long id) throws NotFoundException {
    return pictureMapper
        .pictureToPictureDto(pictureRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  @Override
  public Long create(PictureDto pictureDto) {
    Picture picture = pictureRepository.save(pictureMapper.pictureDtoToPicture(pictureDto));
    return picture.getId();
  }

  @Transactional
  @Override
  public void updateSetNews(DetailedNewsDto newsDto, List<Long> pictureIds) {
    String queryString = "UPDATE Picture SET news = :news WHERE id = :id";

    Query q = entityManager.createQuery(queryString);

    for (Long id : pictureIds) {
      q.setParameter("news", newsMapper.detailedNewsDtoToNews(newsDto));
      q.setParameter("id", id);
      q.executeUpdate();

    }
  }

}

