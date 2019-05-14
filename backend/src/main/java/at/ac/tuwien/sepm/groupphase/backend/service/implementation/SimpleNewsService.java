package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SimpleNewsService implements NewsService {

  private final NewsRepository newsRepository;
  private final PictureRepository pictureRepository;
  private final NewsMapper newsMapper;

  /**
   * News service constructor.
   *
   * @param newsRepository to access news entries
   * @param pictureRepository to get pictures belonging to news entry
   * @param newsMapper to map between dto and entity
   */
  public SimpleNewsService(NewsRepository newsRepository, PictureRepository pictureRepository,
      NewsMapper newsMapper) {
    this.newsRepository = newsRepository;
    this.pictureRepository = pictureRepository;
    this.newsMapper = newsMapper;
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
  public DetailedNewsDto findOne(Long id) throws NotFoundException {
    News news = newsRepository.findById(id).orElseThrow(NotFoundException::new);
    DetailedNewsDto retNewsDto = newsMapper.newsToDetailedNewsDto(news);

    List<Picture> pictures = pictureRepository.findAllByNewsOrderByIdAsc(news);
    List<Long> pictureIds = new ArrayList<>();

    pictures.forEach(p -> pictureIds.add(p.getId()));

    retNewsDto.setPictureIds(pictureIds);

    return retNewsDto;
  }

  @Override
  public DetailedNewsDto create(DetailedNewsDto news) {
    news.setPublishedAt(LocalDateTime.now());
    return newsMapper
        .newsToDetailedNewsDto(newsRepository.save(newsMapper.detailedNewsDtoToNews(news)));
  }
}
