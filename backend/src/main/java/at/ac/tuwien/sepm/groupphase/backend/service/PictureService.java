package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;

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

  /**
   * set the foreign key news for all pictures.
   *
   * @param newsDto the news entry a picture should reference to
   * @param pictureIds ids of the pictures where the key should be set
   */
  void updateSetNews(DetailedNewsDto newsDto, List<Long> pictureIds);
}
