package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PointDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.hall.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.unit.UnitMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.InvalidInputException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleHallService implements HallService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHallService.class);
  private HallRepository hallRepository;
  private UnitRepository unitRepository;
  private LocationRepository locationRepository;
  private HallMapper hallMapper;
  private UnitMapper unitMapper;

  /** javadoc. */
  @Autowired
  public SimpleHallService(
      HallRepository hallRepository,
      UnitRepository unitRepository,
      LocationRepository locationRepository,
      HallMapper hallMapper,
      UnitMapper unitMapper
  ) {
    this.hallRepository = hallRepository;
    this.unitRepository = unitRepository;
    this.locationRepository = locationRepository;
    this.hallMapper = hallMapper;
    this.unitMapper = unitMapper;
  }

  @Override
  public HallDto getOneById(Long id) throws NotFoundException {
    LOGGER.info("Get hall with id " + id);
    return hallMapper.hallToHallDto(
        hallRepository.findById(id).orElseThrow(
            () -> {
              String msg = "Can't find hall with id " + id;
              LOGGER.error(msg);
              return new NotFoundException(msg);
            }
        )
    );
  }

  @Override
  public HallDto create(HallRequestDto hallRequestDto) {
    LOGGER.info("Save hall");
    Hall hall = hallMapper.hallRequestDtoToHall(hallRequestDto);
    List<Unit> units = new ArrayList<>();
    for (UnitDto unitDto : hallRequestDto.getUnits()) {
      units.add(unitMapper.unitDtoToUnit(unitDto));
    }

    LOGGER.info(hall.getName() + " /// " + hall.getBoundaryPoint().getCoordinateX() + " /// "
        + hall.getBoundaryPoint().getCoordinateY());
    for (UnitDto u : hallRequestDto.getUnits()) {
      Unit unit = unitMapper.unitDtoToUnit(u);
      LOGGER.info(unit.getName() + " /// " + unit.getUpperBoundary().getCoordinateX() + " /// "
          + unit.getUpperBoundary().getCoordinateY());
    }

    // todo validate
    List<Unit> seats = new ArrayList<>();
    List<Unit> sectors = new ArrayList<>();
    for (Unit unit : units) {
      if (unit.getCapacity() == 1) {
        seats.add(unit);
      } else if (unit.getCapacity() > 1) {
        sectors.add(unit);
      } else {
        throw new InvalidInputException("Unit capacity must be at least 1");
      }
    }

    // todo remove this when locations are implemented
    Location location = locationRepository.findById(1L).orElseThrow(NotFoundException::new);
    hall.setLocation(location);

    Hall savedHall = hallRepository.save(hall);
    LOGGER.info("Saved hall. Id: " + hall.getId());

    // todo save units
    for (Unit unit : units) {
      unit.setHall(savedHall);
      unitRepository.save(unit);
    }

    LOGGER.info("Saved units for hall with id " + hall.getId());

    return hallMapper.hallToHallDto(savedHall);
  }

  @Override
  public HallDto update(HallRequestDto hallRequestDto) {
    LOGGER.info("Update hall with id " + hallRequestDto.getId());
    return null; // todo implement
  }

  @Override
  public List<UnitDto> getUnitsByHallId(Long id) {
    LOGGER.info("Get units of hall with id " + id);
    List<Unit> units = unitRepository.findAllByHall_Id(id).orElseThrow(NotFoundException::new);

    List<UnitDto> unitDtos = new ArrayList<>();
    for (Unit u : units) {
      unitDtos.add(unitMapper.unitToUnitDto(u));
    }

    return unitDtos;
  }
}
