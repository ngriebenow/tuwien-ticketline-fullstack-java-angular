package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.picture;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PictureMapper {

  Picture pictureDtoToPicture(PictureDto pictureDto);

  PictureDto pictureToPictureDto(Picture picture);
}
