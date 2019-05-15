package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

@ApiModel(value = "PictureDto", description = "A Dto for an picture via rest")
public class PictureDto {

  @ApiModelProperty(name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The picture content")
  private byte[] data;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    PictureDto that = (PictureDto) obj;
    return Objects.equals(id, that.id)
        && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, data);
  }

  /** Build the picture dto. */
  public PictureDto build() {
    PictureDto pictureDto = new PictureDto();
    pictureDto.setId(id);
    pictureDto.setData(data);
    return pictureDto;
  }
}
