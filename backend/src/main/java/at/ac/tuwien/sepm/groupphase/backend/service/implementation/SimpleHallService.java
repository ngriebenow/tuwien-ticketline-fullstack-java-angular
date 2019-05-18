package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

@Service
public class SimpleHallService implements HallService {

  private HallRepository hallRepository;
  private HallMapper hallMapper;

  public SimpleHallService(HallRepository hallRepository, HallMapper hallMapper) {
    this.hallRepository = hallRepository;
    this.hallMapper = hallMapper;
  }

  @Override
  public HallDto getOneById(Long id) throws NotFoundException {
    return hallMapper.hallToHallDto(
        hallRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  @Override
  public HallDto create() {
    return null;
  }

  @Override
  public HallDto update(HallDto hallDto) {
    return null;
  }
}
