package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.LocationFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface LocationService {

  /**
   * Get the location by id.
   *
   * @param id the id of the location
   * @return the location
   * @throws NotFoundException if the id could not be found
   */
  LocationDto getOneById(Long id);

  /**
   * Return a page of events which satisfy the given filter properties.
   *
   * @param locationFilterDto an object containing all the filter criteria.
   * @param pageable the pageable for determining the page.
   * @return a page of locations.
   */
  List<LocationDto> getFiltered(LocationFilterDto locationFilterDto, Pageable pageable);

  /**
   * Get all halls of location by location id.
   *
   * @param id the id of the location
   * @return the list of halls
   * @throws NotFoundException if the id could not be found
   */
  List<HallDto> getHallsByLocationId(Long id);

}
