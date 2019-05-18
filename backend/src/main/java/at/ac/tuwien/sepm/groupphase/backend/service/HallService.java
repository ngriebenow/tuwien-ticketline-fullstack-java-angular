package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

public interface HallService {

  /**
   * Get the hall by id.
   *
   * @param id the id of the hall
   * @return the hall
   * @throws NotFoundException if the id could not be found
   */
  HallDto getOneById(Long id) throws NotFoundException;


  HallDto create();

  /**
   * Updates the hall.
   *
   * @param hallDto the altered hall
   * @return the altered hall that has been saved
   * @throws NotFoundException if the id could not be found
   */
  HallDto update(HallDto hallDto) throws NotFoundException;

}
