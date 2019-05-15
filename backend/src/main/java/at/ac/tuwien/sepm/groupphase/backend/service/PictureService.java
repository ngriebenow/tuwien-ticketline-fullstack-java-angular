package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

public interface PictureService {

  /**
   * Find a single picture by id.
   *
   * @param id the id of the picture
   * @return the picture
   * @throws NotFoundException if the id could not be found
   */
  PictureDto findOne(Long id) throws NotFoundException;

  /**
   * create a picture.
   *
   * @param picture to create
   * @return the pictures id
   */
  Long create(PictureDto picture);
}
