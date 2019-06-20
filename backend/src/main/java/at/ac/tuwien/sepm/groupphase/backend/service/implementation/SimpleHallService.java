package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.hall.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.unit.UnitMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleHallService implements HallService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHallService.class);
  private HallRepository hallRepository;
  private UnitRepository unitRepository;
  private LocationRepository locationRepository;
  private HallMapper hallMapper;
  private UnitMapper unitMapper;

  /**
   * Constructor.
   */
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

  @Transactional
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

  @Transactional
  @Override
  public HallDto create(HallRequestDto hallRequestDto) {
    LOGGER.info("Save hall");
    // convert dtos to entitys
    Hall hall = hallMapper.hallRequestDtoToHall(hallRequestDto);
    List<Unit> units = new ArrayList<>();
    for (UnitDto unitDto : hallRequestDto.getUnits()) {
      units.add(unitMapper.unitDtoToUnit(unitDto));
    }

    hall.setVersion(1);
    hall.setNewVersion(null);

    // validate units
    try {
      validUnits(units, hall.getBoundaryPoint());
    } catch (ValidationException e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
    // calculate seat names
    calculateSeatNames(units);
    // save hall
    Hall savedHall = hallRepository.save(hall);
    LOGGER.info("Saved hall. Id: " + hall.getId());
    // save units
    for (Unit unit : units) {
      unit.setHall(savedHall);
      unitRepository.save(unit);
    }
    LOGGER.info("Saved units for hall with id " + hall.getId());
    return hallMapper.hallToHallDto(savedHall);
  }

  @Transactional
  @Override
  public HallDto update(HallRequestDto hallRequestDto) {
    LOGGER.info("Update hall with id " + hallRequestDto.getId());
    // convert dtos to entitys
    Hall hall = hallMapper.hallRequestDtoToHall(hallRequestDto);
    List<Unit> units = new ArrayList<>();
    for (UnitDto unitDto : hallRequestDto.getUnits()) {
      units.add(unitMapper.unitDtoToUnit(unitDto));
    }

    // get old version
    Hall oldHall = hallRepository.findById(hall.getId()).orElseThrow(
        () -> {
          String msg = "Can't find hall with id " + hall.getId();
          LOGGER.error(msg);
          return new NotFoundException(msg);
        }
    );
    while (oldHall.getNewVersion() != null) {
      oldHall = oldHall.getNewVersion();
    }

    // set version
    hall.setVersion(oldHall.getVersion() + 1);
    hall.setNewVersion(null);
    hall.setId(null);

    // validate units
    try {
      validUnits(units, hall.getBoundaryPoint());
    } catch (ValidationException e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
    // calculate seat names
    calculateSeatNames(units);

    // save hall
    Hall savedHall = hallRepository.save(hall);
    LOGGER.info("Saved hall. Id: " + hall.getId() + " as update of hall " + oldHall.getId());

    // update old hall
    oldHall.setNewVersion(savedHall);
    hallRepository.saveAndFlush(oldHall);

    // save units
    for (Unit unit : units) {
      unit.setHall(savedHall);
      unit.setId(null);
      unitRepository.save(unit);
    }
    LOGGER.info("Saved units for hall with id " + hall.getId());
    return hallMapper.hallToHallDto(savedHall);
  }

  @Transactional
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

  /**
   * Validates all units for overlapping and correct boundary points.
   *
   * @param units != null
   * @throws ValidationException if validation goes wrong
   */
  private void validUnits(List<Unit> units, Point hallSize) throws ValidationException {
    // separate units into seats and sectors
    List<Unit> seats = new ArrayList<>();
    List<Unit> sectors = new ArrayList<>();
    for (Unit unit : units) {
      if (unit.getCapacity() == 1) {
        seats.add(unit);
      } else if (unit.getCapacity() > 1) {
        sectors.add(unit);
      } else {
        throw new ValidationException("Unit capacity must be at least 1");
      }
    }
    // validate seats
    for (Unit seat : seats) {
      if (seat.getUpperBoundary().getCoordinateX() != seat.getLowerBoundary().getCoordinateX()
          || seat.getUpperBoundary().getCoordinateY() != seat.getLowerBoundary().getCoordinateY()
      ) {
        throw new ValidationException("Seat upper boundary must be same as seat lower boundary");
      }
    }
    // validate sectors
    for (Unit sector : sectors) {
      if (sector.getUpperBoundary().getCoordinateX() > sector.getLowerBoundary().getCoordinateX()
          || sector.getUpperBoundary().getCoordinateY() > sector.getLowerBoundary().getCoordinateY()
      ) {
        throw new ValidationException(
            "Sector lower boundary must not be above or left of upper boundary");
      }
    }
    // validate units
    for (int i = 0; i < units.size(); i++) {
      Unit unit1 = units.get(i);
      for (int j = i + 1; j < units.size(); j++) {
        if (unitsOverlapping(unit1, units.get(j))) {
          throw new ValidationException("Units must not overlap each other " + unit1.getName()
              + unit1.getUpperBoundary().getCoordinateX()
              + "/" + unit1.getUpperBoundary().getCoordinateY()
              + " / " + units.get(j).getName() + units.get(j).getUpperBoundary().getCoordinateX()
              + "/" + units.get(j).getUpperBoundary().getCoordinateY());
        }
      }
      if (unit1.getUpperBoundary().getCoordinateX() < 1
          || unit1.getUpperBoundary().getCoordinateY() < 1
          || unit1.getLowerBoundary().getCoordinateX() > hallSize.getCoordinateX()
          || unit1.getLowerBoundary().getCoordinateY() > hallSize.getCoordinateY()
      ) {
        throw new ValidationException(
            "Unit must be inside hall " + unit1.getName());
      }
    }
  }

  /**
   * Evaluates if two units overlap each other.
   *
   * @param unit1 != null
   * @param unit2 != null
   * @return true if unit1 overlaps unit2
   */
  private boolean unitsOverlapping(Unit unit1, Unit unit2) {
    return !(unit1.getLowerBoundary().getCoordinateX() < unit2.getUpperBoundary().getCoordinateX()
        || unit2.getLowerBoundary().getCoordinateX() < unit1.getUpperBoundary().getCoordinateX()
        || unit1.getLowerBoundary().getCoordinateY() < unit2.getUpperBoundary().getCoordinateY()
        || unit2.getLowerBoundary().getCoordinateY() < unit1.getUpperBoundary().getCoordinateY());
  }

  /**
   * Calculates names for all seats starting with "Reihe: 1 Sitz: 1" for first seat in first row
   * that has seats.
   *
   * @param units != null
   */
  private void calculateSeatNames(List<Unit> units) {
    int rowCounter = 1;
    int columnCounter = 1;

    List<Unit> seats = new ArrayList<>();
    for (Unit unit : units) {
      if (unit.getCapacity() == 1) {
        seats.add(unit);
      }
    }
    seats.sort(Comparator.comparing((Unit u) -> u.getUpperBoundary().getCoordinateY())
        .thenComparing((Unit u) -> u.getUpperBoundary().getCoordinateX()));

    for (Unit seat : seats) {
      if (seat.getUpperBoundary().getCoordinateY() == rowCounter) {
        seat.setName("Reihe: " + rowCounter + " Sitz: " + columnCounter++);
      } else {
        rowCounter++;
        columnCounter = 1;
      }
    }
  }
}
