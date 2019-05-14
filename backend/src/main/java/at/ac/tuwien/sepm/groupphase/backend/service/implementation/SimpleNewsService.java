package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SimpleNewsService implements NewsService {
  private final NewsRepository newsRepository;

  public SimpleNewsService(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  @Override
  public List<News> findAll() {
    return newsRepository.findAll();
  }

  @Override
  public News findOne(Long id) throws NotFoundException {
    return newsRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public News create(News news) {
    news.setPublishedAt(LocalDateTime.now());
    return newsRepository.save(news);
  }
}
