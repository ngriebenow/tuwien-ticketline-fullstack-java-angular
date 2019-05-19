package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UnitDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;

public interface HallService {

  /**
   * Get the hall by id.
   *
   * @param id the id of the hall
   * @return the hall
   * @throws NotFoundException if the id could not be found
   */
  HallDto getOneById(Long id) throws NotFoundException;

  /**
   * Save the hall.
   *
   * @param hallDto the hall to be saved
   * @return the hall that has been saved
   */
  HallDto create(HallDto hallDto);

  /**
   * Update the hall.
   *
   * @param hallDto the altered hall
   * @return the altered hall that has been saved
   * @throws NotFoundException if the id could not be found
   */
  HallDto update(HallDto hallDto) throws NotFoundException;

  /**
   * Get all units in hall by hall id.
   *
   * @param id the id of the hall
   * @return the list of units
   * @throws NotFoundException if the id could not be found
   */
  List<UnitDto> getUnitsByHallId(Long id) throws NotFoundException;

}
