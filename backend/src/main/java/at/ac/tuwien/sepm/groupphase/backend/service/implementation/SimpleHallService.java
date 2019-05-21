package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PointDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.hall.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.unit.UnitMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
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
  private HallMapper hallMapper;
  private UnitMapper unitMapper;

  /** javadoc. */
  @Autowired
  public SimpleHallService(
      HallRepository hallRepository,
      UnitRepository unitRepository,
      HallMapper hallMapper,
      UnitMapper unitMapper
  ) {
    this.hallRepository = hallRepository;
    this.unitRepository = unitRepository;
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
            }));
  }

  @Override
  public HallDto create(HallRequestDto hallRequestDto) {
    LOGGER.info("Save hall");
    Hall hall = hallMapper.hallRequestDtoToHall(hallRequestDto);
    LOGGER.info(hall.getName() + " /// " + hall.getBoundaryPoint().getCoordinateX() + " /// "
        + hall.getBoundaryPoint().getCoordinateY());
    for (UnitDto u : hallRequestDto.getUnits()) {
      Unit unit = unitMapper.unitDtoToUnit(u);
      LOGGER.info(unit.getName() + " /// " + unit.getUpperBoundary().getCoordinateX() + " /// "
          + unit.getUpperBoundary().getCoordinateY());
    }
    return null; // todo implement
  }

  @Override
  public HallDto update(HallRequestDto hallRequestDto) {
    LOGGER.info("Update hall with id " + hallRequestDto.getId());
    return null; // todo implement
  }

  @Override
  public List<UnitDto> getUnitsByHallId(Long id) {
    LOGGER.info("Get units of hall with id " + id);
    List<Unit> units = unitRepository.findAllByHall_Id(id);
    List<UnitDto> unitDtos = new ArrayList<>();

    for (Unit u : units) {
      unitDtos.add(unitMapper.unitToUnitDto(u));
    }

    // todo delete (just for testing)
    PointDto upper = new PointDto();
    PointDto lower = new PointDto();
    upper.setCoordinateX(2);
    upper.setCoordinateY(4);
    lower.setCoordinateX(4);
    lower.setCoordinateY(8);
    UnitDto sector = new UnitDto();
    sector.setLowerBoundary(lower);
    sector.setUpperBoundary(upper);
    sector.setCapacity(9);
    unitDtos.add(sector);

    return unitDtos;
  }
}
