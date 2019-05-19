package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.hall;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HallMapper {

  Hall hallDtoToHall(HallDto hallDto);

  HallDto hallToHallDto(Hall hall);
}
