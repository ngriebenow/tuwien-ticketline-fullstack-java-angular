package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsRead;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsReadRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleNewsService implements NewsService {

  private final NewsRepository newsRepository;
  private final NewsReadRepository newsReadRepository;
  private final PictureRepository pictureRepository;
  private final NewsMapper newsMapper;
  private final EntityManager entityManager;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SimpleNewsService.class);

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

  @Transactional
  @Override
  public List<SimpleNewsDto> findAllNew(User user, Pageable pageable) {
    LOGGER.info("get all unread news");

    String queryString = "SELECT n FROM News n "
        + "WHERE NOT EXISTS (SELECT nr FROM NewsRead nr WHERE nr.news = n AND nr.user = :user)"
        + "ORDER BY n.publishedAt DESC";

    TypedQuery<News> q = entityManager.createQuery(queryString, News.class);

    q.setParameter("user", user);
    List<SimpleNewsDto> newsDtos = new ArrayList<>();

    if (pageable.isPaged()) {
      q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
      q.setMaxResults(pageable.getPageSize());
    }

    List<News> news = q.getResultList();
    for (News entry : news) {
      SimpleNewsDto newsDto = newsMapper.newsToSimpleNewsDto(entry);
      newsDto.setRead(false);
      newsDtos.add(newsDto);
    }
    return newsDtos;
  }

  @Transactional
  @Override
  public List<SimpleNewsDto> findAll(User user, Pageable pageable) {
    LOGGER.info("get all news");

    List<SimpleNewsDto> unreadNews = findAllNew(user, pageable);

    List<SimpleNewsDto> newsDtos = new ArrayList<>();
    List<News> news = new ArrayList<>();
    news = newsRepository.findAllByOrderByPublishedAtDesc(pageable);
    for (News entry : news) {
      SimpleNewsDto newsDto = newsMapper.newsToSimpleNewsDto(entry);
      if (unreadNews.contains(newsDto)) {
        newsDto.setRead(false);
      } else {
        newsDto.setRead(true);
      }
      newsDtos.add(newsDto);
    }
    return newsDtos;
  }

  @Transactional
  @Override
  public DetailedNewsDto findOne(Long id, User user) throws NotFoundException {
    LOGGER.info("getNewsById " + id);

    News news = newsRepository.findById(id).orElseThrow(
        () -> {
          String msg = "Can't find news with id " + id;
          LOGGER.error(msg);
          return new NotFoundException(msg);
        });

    //Mark news entry as read
    newsReadRepository.save(new NewsRead.Builder()
        .news(news)
        .user(user)
        .build());

    DetailedNewsDto retNewsDto = newsMapper.newsToDetailedNewsDto(news);

    //Get all picture for news entry

    List<Picture> pictures = pictureRepository.findAllByNewsOrderByIdAsc(news);
    List<Long> pictureIds = new ArrayList<>();

    if (pictures != null) {
      pictures.forEach(p -> pictureIds.add(p.getId()));
    }
    retNewsDto.setPictureIds(pictureIds);

    return retNewsDto;
  }

  @Transactional
  @Override
  public DetailedNewsDto create(DetailedNewsDto news) {
    LOGGER.info("create news " + news);

    validateDetailedNewsDto(news);
    news.setPublishedAt(LocalDateTime.now());
    return newsMapper
        .newsToDetailedNewsDto(newsRepository.save(newsMapper.detailedNewsDtoToNews(news)));
  }

  private void validateDetailedNewsDto(DetailedNewsDto news) {
    final String empty_summary_msg = "Error while creating news, Summary must not be empty.";
    final String empty_text_msg = "Error while creating news, Text must not be empty.";
    final String empty_title_msg = "Error while creating news, Title must not be empty.";
    if (news.getSummary() == null) {
      LOGGER.error(empty_summary_msg);
      throw new ValidationException(empty_summary_msg);
    } else if (news.getSummary().isEmpty()) {
      LOGGER.error(empty_summary_msg);
      throw new ValidationException(empty_summary_msg);
    }
    if (news.getText() == null) {
      LOGGER.error(empty_text_msg);
      throw new ValidationException(empty_text_msg);
    } else if (news.getText().isEmpty()) {
      LOGGER.error(empty_text_msg);
      throw new ValidationException(empty_text_msg);
    }
    if (news.getTitle() == null) {
      LOGGER.error(empty_title_msg);
      throw new ValidationException(empty_title_msg);
    } else if (news.getTitle().isEmpty()) {
      LOGGER.error(empty_title_msg);
      throw new ValidationException(empty_title_msg);
    }

    //Check if picture ids exists
    if (news.getPictureIds() != null) {
      for (Long id : news.getPictureIds()) {
        if (!pictureRepository.existsById(id)) {
          String msg = "Error while creating news, Picture with id " + id + " does not exist.";
          LOGGER.error(msg);
          throw new ValidationException(msg);
        }
      }
    }


  }


}
