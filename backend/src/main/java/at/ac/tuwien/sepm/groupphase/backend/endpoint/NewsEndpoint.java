package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthenticationConstants;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
import at.ac.tuwien.sepm.groupphase.backend.service.implementation.SimpleHeaderTokenAuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/news")
@Api(value = "news")
public class NewsEndpoint {

  private final NewsService newsService;
  private final PictureService pictureService;
  private final SimpleHeaderTokenAuthenticationService tokenAuthenticationService;
  private final AccountService accountService;

  /**
   * Construct the entity.
   */
  public NewsEndpoint(NewsService newsService, PictureService pictureService,
      SimpleHeaderTokenAuthenticationService tokenAuthenticationService,
      AccountService accountService) {
    this.newsService = newsService;
    this.pictureService = pictureService;
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.accountService = accountService;
  }

  /**
   * Get the logged in user.
   *
   * @param authorizationHeader token
   * @return the currently logged in user
   */
  private User getUser(String authorizationHeader) {

    String trimmedHeader = authorizationHeader
        .substring(AuthenticationConstants.TOKEN_PREFIX.length()).trim();

    return accountService.findOne(tokenAuthenticationService
        .authenticationTokenInfo(trimmedHeader).getUsername());
  }

  /**
   * Get all (recent) news entries.
   * @param onlyNew if not specified then false
   * @param authorizationHeader to get user
   * @return a list of news entries
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(
      value = "Get list of simple news entries",
      authorizations = {@Authorization(value = "apiKey")})
  public List<SimpleNewsDto> findAll(@RequestParam(required = false) boolean onlyNew,
      @ApiIgnore @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
    if (onlyNew) {
      return newsService.findAllNew(getUser(authorizationHeader));
    }  else {
      return newsService.findAll();
    }
  }

  /**
   * Get news entry with id.
   *
   * @param id of the news entry
   * @param authorizationHeader to get user and eventually mark news entry as read
   * @return the detailed news entry with given id
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get detailed information about a specific news entry",
      authorizations = {@Authorization(value = "apiKey")})
  public DetailedNewsDto find(@PathVariable Long id,
      @ApiIgnore @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
    return newsService.findOne(id, getUser(authorizationHeader));
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
