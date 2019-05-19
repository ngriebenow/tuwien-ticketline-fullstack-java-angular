package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.artist;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

  Artist artistDtoToArtist(ArtistDto artistDto);

  ArtistDto artistToArtistDto(Artist artist);
}
