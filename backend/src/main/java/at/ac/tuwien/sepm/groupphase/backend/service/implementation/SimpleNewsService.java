package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SimpleNewsService implements NewsService {

  private final NewsRepository newsRepository;
  private final NewsMapper newsMapper;

  public SimpleNewsService(NewsRepository newsRepository, NewsMapper newsMapper) {
    this.newsRepository = newsRepository;
    this.newsMapper = newsMapper;
  }

  @Override
  public List<SimpleNewsDto> findAll() {
    List<SimpleNewsDto> newsDtos = new ArrayList<>();
    List<News> news = new ArrayList<>();
    news = newsRepository.findAll();
    news.forEach(n -> newsDtos.add(newsMapper.newsToSimpleNewsDto(n)));
    return newsDtos;
  }

  @Override
  public DetailedNewsDto findOne(Long id) throws NotFoundException {
    return newsMapper
        .newsToDetailedNewsDto(newsRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  @Override
  public DetailedNewsDto create(DetailedNewsDto news) {
    news.setPublishedAt(LocalDateTime.now());
    return newsMapper
        .newsToDetailedNewsDto(newsRepository.save(newsMapper.detailedNewsDtoToNews(news)));
  }
}
