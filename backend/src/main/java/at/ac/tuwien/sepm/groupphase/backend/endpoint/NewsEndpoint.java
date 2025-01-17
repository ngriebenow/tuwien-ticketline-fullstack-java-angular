package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/news")
@Api(value = "news")
public class NewsEndpoint {

  private final NewsService newsService;
  private final PictureService pictureService;
  private final AccountService accountService;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(NewsEndpoint.class);

  /**
   * Construct the entity.
   */
  public NewsEndpoint(NewsService newsService, PictureService pictureService,
      AccountService accountService) {
    this.newsService = newsService;
    this.pictureService = pictureService;
    this.accountService = accountService;
  }

  /**
   * Get the logged in user.
   *
   * @return the currently logged in user
   */
  private User getUser() {

    org.springframework.security.core.userdetails.User
        tmp = (org.springframework.security.core.userdetails.User)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return accountService.findOne(tmp.getUsername());
  }

  /**
   * Get all (recent) news entries.
   *
   * @param onlyNew if not specified then false
   * @param page the number of page
   * @param count the size of a page
   * @return a list of news entries
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(
      value = "Get list of simple news entries",
      authorizations = {@Authorization(value = "apiKey")})
  public List<SimpleNewsDto> findAll(@RequestParam(required = false) boolean onlyNew,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer count) {

    Pageable p = getPageable(page, count);

    if (onlyNew) {
      LOGGER.info("get all unread news");
      return newsService.findAllNew(getUser(), p);
    } else {
      LOGGER.info("get all news");
      return newsService.findAll(getUser(), p);
    }
  }

  private Pageable getPageable(Integer page, Integer count) {
    Pageable p;
    if (page != null && count != null) {
      p = PageRequest.of(page, count);
    } else {
      p = Pageable.unpaged();
    }
    return p;
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
    LOGGER.info("getNewsById " + id);
    return newsService.findOne(id, getUser());
  }

  /**
   * Create a news entry.
   *
   * @param newsDto the new detailed news entry
   * @return the created detailed news entry
   */
  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Publish a news entry",
      authorizations = {@Authorization(value = "apiKey")})
  public DetailedNewsDto publishNews(@RequestBody DetailedNewsDto newsDto) {
    LOGGER.info("create news " + newsDto);
    DetailedNewsDto retNewsDto = newsService.create(newsDto);
    if (newsDto.getPictureIds() != null) {
      pictureService.updateSetNews(retNewsDto, newsDto.getPictureIds());
    }
    retNewsDto.setPictureIds(newsDto.getPictureIds());
    return retNewsDto;
  }
}
