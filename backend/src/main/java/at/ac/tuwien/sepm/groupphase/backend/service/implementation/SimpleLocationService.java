package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.LocationFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.location.LocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepm.groupphase.backend.specification.LocationSpecification;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleLocationService implements LocationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLocationService.class);

  private LocationRepository locationRepository;
  private LocationMapper locationMapper;

  @Autowired
  public SimpleLocationService(
      LocationRepository locationRepository,
      LocationMapper locationMapper
  ) {
    this.locationRepository = locationRepository;
    this.locationMapper = locationMapper;
  }

  @Transactional(readOnly = true)
  @Override
  public List<LocationDto> getFiltered(LocationFilterDto locationFilterDto, Pageable pageable) {
    LOGGER.info("Get locations filtered");
    return locationRepository
        .findAll(LocationSpecification.buildFilterSpecification(locationFilterDto), pageable)
        .map(locationMapper::locationToLocationDto)
        .getContent();
  }
}
