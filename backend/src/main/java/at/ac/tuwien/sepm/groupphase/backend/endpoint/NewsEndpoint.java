package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/news")
@Api(value = "news")
public class NewsEndpoint {

  private final NewsService newsService;
  private final NewsMapper newsMapper;

  public NewsEndpoint(NewsService newsService, NewsMapper newsMapper) {
    this.newsService = newsService;
    this.newsMapper = newsMapper;
  }

  /**
   * Get all news entries.
   *
   * @return a list of news entries
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(
      value = "Get list of simple news entries",
      authorizations = {@Authorization(value = "apiKey")})
  public List<SimpleNewsDto> findAll() {
    return newsMapper.newsToSimpleNewsDto(newsService.findAll());
  }

  /**
   * Get news entry with id.
   *
   * @param id of the news entry
   * @return the detailed news entry with given id
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get detailed information about a specific news entry",
      authorizations = {@Authorization(value = "apiKey")})
  public DetailedNewsDto find(@PathVariable Long id) {
    return newsMapper.newsToDetailedNewsDto(newsService.findOne(id));
  }

  /**
   * Create a news entry.
   *
   * @param detailedNewsDto the new detailed news entry
   * @return the created detailed news entry
   */
  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Publish a news entry",
      authorizations = {@Authorization(value = "apiKey")})
  public DetailedNewsDto publishNews(@RequestBody DetailedNewsDto detailedNewsDto) {
    News news = newsMapper.detailedNewsDtoToNews(detailedNewsDto);
    news = newsService.create(news);
    return newsMapper.newsToDetailedNewsDto(news);
  }
}
