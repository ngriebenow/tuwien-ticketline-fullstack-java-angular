package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.picture.PictureMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PictureService;
import org.springframework.stereotype.Service;

@Service
public class SimplePictureService implements PictureService {

  private final PictureRepository pictureRepository;
  private final PictureMapper pictureMapper;

  /**
   * picture service constructor.
   *
   * @param pictureRepository to get pictures belonging to news entry
   * @param pictureMapper to map between dto and entity
   */
  public SimplePictureService(PictureRepository pictureRepository,
      PictureMapper pictureMapper) {
    this.pictureRepository = pictureRepository;
    this.pictureMapper = pictureMapper;
  }

  @Override
  public PictureDto findOne(Long id) throws NotFoundException {
    return pictureMapper
        .pictureToPictureDto(pictureRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  @Override
  public Long create(PictureDto pictureDto) {
    Picture picture = pictureRepository.save(pictureMapper.pictureDtoToPicture(pictureDto));
    return picture.getId();
  }
}

