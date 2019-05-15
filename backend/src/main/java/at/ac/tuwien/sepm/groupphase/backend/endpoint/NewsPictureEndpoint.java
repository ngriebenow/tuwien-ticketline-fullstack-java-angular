package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/newspictures")
@Api(value = "newspictures")
public class NewsPictureEndpoint {

  private final PictureService pictureService;

  public NewsPictureEndpoint(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  /**
   * Get picture with id.
   *
   * @param id of the picture
   * @return the picture
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get picture dto",
      authorizations = {@Authorization(value = "apiKey")})
  public PictureDto get(@PathVariable Long id) throws NotFoundException {
    return pictureService.findOne(id);
  }

  /**
   * Create a picture.
   *
   * @param pictureDto the picture to create
   * @return the id of the created picture
   */
  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "create a picture",
      authorizations = {@Authorization(value = "apiKey")})
  public Long post(@RequestBody PictureDto pictureDto) {
    return pictureService.create(pictureDto);
  }
}

