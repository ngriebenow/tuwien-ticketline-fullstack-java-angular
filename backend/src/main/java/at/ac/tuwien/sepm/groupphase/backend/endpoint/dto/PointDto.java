package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PointDto", description = "A DTO for a point via rest")
public class PointDto {

  @ApiModelProperty(required = true, name = "The x coordinate of the point")
  private int coordinateX;

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

  /** Build the point dto */
  public PointDto build() {
    PointDto pointDto = new PointDto();
    pointDto.setCoordinateX(coordinateX);
    pointDto.setCoordinateY(coordinateY);
    return pointDto;
  }
}
