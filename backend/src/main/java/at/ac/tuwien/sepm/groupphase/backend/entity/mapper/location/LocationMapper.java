package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.location;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

  Location locationDtoToLocation(LocationDto locationDto);

  LocationDto locationToLocationDto(Location location);
}
