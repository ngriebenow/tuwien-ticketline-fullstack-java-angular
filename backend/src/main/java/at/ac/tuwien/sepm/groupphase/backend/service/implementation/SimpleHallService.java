package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UnitDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.hall.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleHallService implements HallService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHallService.class);
  private HallRepository hallRepository;
  private HallMapper hallMapper;

  @Autowired
  public SimpleHallService(HallRepository hallRepository, HallMapper hallMapper) {
    this.hallRepository = hallRepository;
    this.hallMapper = hallMapper;
  }

  @Override
  public HallDto getOneById(Long id) throws NotFoundException {
    return hallMapper.hallToHallDto(
        hallRepository.findById(id).orElseThrow(
            () -> {
              String msg = "Can't find hall with id " + id;
              LOGGER.error(msg);
              return new NotFoundException(msg);
            }));
  }

  @Override
  public HallDto create(HallDto hallDto) {
    return null; // todo implement
  }

  @Override
  public HallDto update(HallDto hallDto) {
    return null; // todo implement
  }

  @Override
  public List<UnitDto> getUnitsByHallId(Long id) {
    return null; // todo implement
  }
}
