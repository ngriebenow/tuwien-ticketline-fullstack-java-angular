package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public class SimplePerformanceService implements PerformanceService {

  @Autowired private PerformanceRepository performanceRepository;

  @Autowired private PerformanceMapper performanceMapper;

  @Autowired private EntityManager em;

  /** Javadoc. */
  public static Specification<Performance> perfName(String name) {
    return new Specification<Performance>() {
      @Override
      public Predicate toPredicate(
          Root<Performance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("name"), name);
      }
    };
  }

  @Override
  public PerformanceDto getOneById(Long id) throws NotFoundException {
    return null;
  }

  @Override
  public List<PerformanceDto> getPerformancesFiltered(
      Specification<Performance> specification, Pageable pageable) {

    Specification<Performance> spec = perfName("Perf 2");

    List<PerformanceDto> performanceDtos = new ArrayList<>();
    performanceRepository
        .findAll(spec, pageable)
        .forEach(e -> performanceDtos.add(performanceMapper.performanceToPerformanceDto(e)));
    return performanceDtos;
  }
}
