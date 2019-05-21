package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallRequestDto;
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
   * @param hallRequestDto the hall to be saved
   * @return the hall that has been saved
   */
  HallDto create(HallRequestDto hallRequestDto);

  /**
   * Update the hall.
   *
   * @param hallRequestDto the altered hall
   * @return the altered hall that has been saved
   * @throws NotFoundException if the id could not be found
   */
  HallDto update(HallRequestDto hallRequestDto) throws NotFoundException;

  /**
   * Get all units in hall by hall id.
   *
   * @param id the id of the hall
   * @return the list of units
   * @throws NotFoundException if the id could not be found
   */
  List<UnitDto> getUnitsByHallId(Long id) throws NotFoundException;

}
