package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DefinedUnitDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PriceCategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.definedunit.DefinedUnitMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.pricecategory.PriceCategoryMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimplePerformanceService implements PerformanceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceService.class);
  @Autowired private DefinedUnitRepository definedUnitRepository;
  @Autowired private DefinedUnitMapper definedUnitMapper;
  @Autowired private PriceCategoryMapper priceCategoryMapper;
  @Autowired private PriceCategoryRepository priceCategoryRepo;

  @Transactional(readOnly = true)
  @Override
  public List<DefinedUnitDto> getDefinedUnitsByPerformanceId(Performance performance) throws NotFoundException {
    LOGGER.info("getDefinedUnitsByPerformanceId " + performance.getId());
    List<DefinedUnitDto> definedUnitDtos = new ArrayList<>();
    try {
      definedUnitRepository.findAllByPerformanceIsLike(performance).forEach
          (e -> definedUnitDtos.add(definedUnitMapper.definedUnitToDto(e)));
    } catch (NotFoundException e){
      LOGGER.error("Could not get defined units " + e.getMessage());
    }
    return definedUnitDtos;
  }

  @Transactional(readOnly = true)
  @Override
  public List<PriceCategoryDto> getPriceCategoriesByEventId(Event event) throws NotFoundException {
    LOGGER.info("getPriceCategoriesByEventId " + event.getId());
    List<PriceCategoryDto> priceCategoryDtos = new ArrayList<>();
    try {
      priceCategoryRepo.findAllByEventOrderByPriceInCentsAsc(event).forEach(
          e -> priceCategoryDtos.add(priceCategoryMapper.priceCategoryToPriceCategoryDto(e)));
    } catch (NotFoundException e) {
      LOGGER.error("Could not get price categories " + e.getMessage());
    }
    return priceCategoryDtos;
  }
}
