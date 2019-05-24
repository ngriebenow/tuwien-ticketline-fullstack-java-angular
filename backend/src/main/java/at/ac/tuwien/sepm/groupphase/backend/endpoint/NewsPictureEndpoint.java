package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/newspictures")
@Api(value = "newspictures")
public class NewsPictureEndpoint {

  private final PictureService pictureService;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(NewsPictureEndpoint.class);

  public NewsPictureEndpoint(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  /**
   * Get picture with id.
   *
   * @param id of the picture
   * @return the picture
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET,
      produces = MediaType.IMAGE_PNG_VALUE)
  @ApiOperation(
      value = "Get picture dto",
      authorizations = {@Authorization(value = "apiKey")})
  public byte[] get(@PathVariable Long id) throws NotFoundException {
    LOGGER.info("get picture by id");
    return pictureService.findOne(id).getData();
  }

  /**
   * Create a picture.
   *
   * @param pictureDto the picture to create
   * @return the id of the created picture
   */
  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "create a picture",
      authorizations = {@Authorization(value = "apiKey")})
  public Long post(@RequestBody PictureDto pictureDto) {
    LOGGER.info("create picture");
    return pictureService.create(pictureDto);
  }
}

