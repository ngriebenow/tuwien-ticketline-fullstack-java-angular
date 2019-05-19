package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsRead;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsReadRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleNewsService implements NewsService {

  private final NewsRepository newsRepository;
  private final NewsReadRepository newsReadRepository;
  private final PictureRepository pictureRepository;
  private final NewsMapper newsMapper;
  private final EntityManager entityManager;


  /**
   * News service constructor.
   *
   * @param newsRepository to access news entries
   * @param newsReadRepository to mark news as read
   * @param pictureRepository to get pictures belonging to news entry
   * @param newsMapper to map between dto and entity
   * @param entityManager for the select query
   */
  public SimpleNewsService(NewsRepository newsRepository,
      NewsReadRepository newsReadRepository, PictureRepository pictureRepository,
      NewsMapper newsMapper, EntityManager entityManager) {
    this.newsRepository = newsRepository;
    this.newsReadRepository = newsReadRepository;
    this.pictureRepository = pictureRepository;
    this.newsMapper = newsMapper;
    this.entityManager = entityManager;
  }

  @Override
  public List<SimpleNewsDto> findAllNew(User user) {

    String queryString = "SELECT n FROM News n "
        + "WHERE NOT EXISTS (SELECT nr FROM NewsRead nr WHERE nr.news = n AND nr.user = :user)"
        + "ORDER BY n.publishedAt DESC";

    TypedQuery<News> q = entityManager.createQuery(queryString, News.class);

    q.setParameter("user", user);
    List<SimpleNewsDto> newsDtos = new ArrayList<>();
    List<News> news = q.getResultList();
    news.forEach(n -> newsDtos.add(newsMapper.newsToSimpleNewsDto(n)));
    return newsDtos;
  }

  @Override
  public List<SimpleNewsDto> findAll() {
    List<SimpleNewsDto> newsDtos = new ArrayList<>();
    List<News> news = new ArrayList<>();
    news = newsRepository.findAllByOrderByPublishedAtDesc();
    news.forEach(n -> newsDtos.add(newsMapper.newsToSimpleNewsDto(n)));
    return newsDtos;
  }

  @Override
  public DetailedNewsDto findOne(Long id, User user) throws NotFoundException {
    News news = newsRepository.findById(id).orElseThrow(NotFoundException::new);

    //Mark news entry as read
    newsReadRepository.save(new NewsRead.Builder()
        .news(news)
        .user(user)
        .build());

    DetailedNewsDto retNewsDto = newsMapper.newsToDetailedNewsDto(news);

    //Get all picture for news entry

    List<Picture> pictures = pictureRepository.findAllByNewsOrderByIdAsc(news);
    List<Long> pictureIds = new ArrayList<>();

    pictures.forEach(p -> pictureIds.add(p.getId()));

    retNewsDto.setPictureIds(pictureIds);

    return retNewsDto;
  }

  @Transactional
  @Override
  public DetailedNewsDto create(DetailedNewsDto news) {
    news.setPublishedAt(LocalDateTime.now());
    return newsMapper
        .newsToDetailedNewsDto(newsRepository.save(newsMapper.detailedNewsDtoToNews(news)));
  }
}
