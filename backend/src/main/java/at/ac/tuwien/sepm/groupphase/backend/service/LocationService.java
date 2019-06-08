package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.LocationFilterDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface LocationService {

  /**
   * Return a page of events which satisfy the given filter properties.
   *
   * @param locationFilterDto an object containing all the filter criteria.
   * @param pageable the pageable for determining the page.
   * @return a page of locations.
   */
  List<LocationDto> getFiltered(LocationFilterDto locationFilterDto, Pageable pageable);

}
