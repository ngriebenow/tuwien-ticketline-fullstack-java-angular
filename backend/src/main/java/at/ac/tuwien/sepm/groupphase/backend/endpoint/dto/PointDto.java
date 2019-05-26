package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(value = "PointDto", description = "A DTO for a point via rest")
public class PointDto {

  @NotNull
  @Min(value = 1, message = "coordinateX must be at least 1")
  @Max(value = 27, message = "coordinateX must be at max 27")
  @ApiModelProperty(required = true, name = "The x coordinate of the point")
  private int coordinateX;

  @NotNull
  @Min(value = 1, message = "coordinateY must be at least 1")
  @Max(value = 27, message = "coordinateY must be at max 27")
  @ApiModelProperty(required = true, name = "The y coordinate of the point")
  private int coordinateY;

  public int getCoordinateX() {
    return coordinateX;
  }

  public void setCoordinateX(int coordinateX) {
    this.coordinateX = coordinateX;
  }

  public int getCoordinateY() {
    return coordinateY;
  }

  public void setCoordinateY(int coordinateY) {
    this.coordinateY = coordinateY;
  }

  /**
   * Build the point dto.
   */
  public PointDto build() {
    PointDto pointDto = new PointDto();
    pointDto.setCoordinateX(coordinateX);
    pointDto.setCoordinateY(coordinateY);
    return pointDto;
  }
}
