package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.LocationFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.hall.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.location.LocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepm.groupphase.backend.specification.LocationSpecification;
import java.util.ArrayList;
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
  private HallRepository hallRepository;
  private LocationMapper locationMapper;
  private HallMapper hallMapper;

  /**
   * Constructor.
   */
  @Autowired
  public SimpleLocationService(
      LocationRepository locationRepository,
      HallRepository hallRepository,
      LocationMapper locationMapper,
      HallMapper hallMapper
  ) {
    this.locationRepository = locationRepository;
    this.hallRepository = hallRepository;
    this.locationMapper = locationMapper;
    this.hallMapper = hallMapper;
  }

  @Transactional
  @Override
  public LocationDto getOneById(Long id) throws NotFoundException {
    LOGGER.info("Get location with id " + id);
    return locationMapper.locationToLocationDto(
        locationRepository.findById(id).orElseThrow(
            () -> {
              String msg = "Can't find location with id " + id;
              LOGGER.error(msg);
              return new NotFoundException(msg);
            }
        )
    );
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

  @Transactional
  @Override
  public List<HallDto> getHallsByLocationId(Long id) throws NotFoundException {
    LOGGER.info("Get halls of location with id " + id);
    Location location = new Location();
    location.setId(id);
    List<Hall> halls = hallRepository
        .findAllByLocationIsLikeAndNewVersionIsNullOrderByName(location)
        .orElseThrow(NotFoundException::new);
    List<HallDto> hallDtos = new ArrayList<>();
    for (Hall h : halls) {
      hallDtos.add(hallMapper.hallToHallDto(h));
    }
    return hallDtos;
  }

  @Transactional
  @Override
  public LocationDto create(LocationDto locationDto) {
    LOGGER.info("Save location");
    Location location = locationMapper.locationDtoToLocation(locationDto);
    return locationMapper.locationToLocationDto(locationRepository.save(location));
  }
}
