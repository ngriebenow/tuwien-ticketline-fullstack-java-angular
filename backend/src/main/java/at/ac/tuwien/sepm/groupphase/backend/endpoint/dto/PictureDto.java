package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PictureDto", description = "A Dto for an picture via rest")
public class PictureDto {

  @ApiModelProperty(name = "The automatically generated database id")
  private long id;

  @ApiModelProperty(required = true, name = "The picture content")
  private byte[] data;

  private PictureDto(Builder builder) {
    setData(builder.data);
  }

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

  public static final class Builder {

    private byte[] data;

    public Builder() {}

    public Builder data(byte[] val) {
      data = val;
      return this;
    }

    public PictureDto build() {
      return new PictureDto(this);
    }
  }
}
