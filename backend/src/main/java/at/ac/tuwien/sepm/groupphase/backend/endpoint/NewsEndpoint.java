package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
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
  private final PictureService pictureService;

  public NewsEndpoint(NewsService newsService, PictureService pictureService) {
    this.newsService = newsService;
    this.pictureService = pictureService;
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
    return newsService.findAll();
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
  public DetailedNewsDto find(@PathVariable Long id) throws NotFoundException {
    return newsService.findOne(id);
  }

  /**
   * Create a news entry.
   *
   * @param newsDto the new detailed news entry
   * @return the created detailed news entry
   */
  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Publish a news entry",
      authorizations = {@Authorization(value = "apiKey")})
  public DetailedNewsDto publishNews(@RequestBody DetailedNewsDto newsDto) {
    DetailedNewsDto retNewsDto = newsService.create(newsDto);
    pictureService.updateSetNews(retNewsDto, newsDto.getPictureIds());
    retNewsDto.setPictureIds(newsDto.getPictureIds());
    return retNewsDto;
  }
}
