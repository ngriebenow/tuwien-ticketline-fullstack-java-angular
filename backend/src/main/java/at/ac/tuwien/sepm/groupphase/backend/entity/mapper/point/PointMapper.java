package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.point;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PointDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {

  Point pointDtoToPoint(PointDto pointDto);

  PointDto pointToPointDto(Point point);
}
