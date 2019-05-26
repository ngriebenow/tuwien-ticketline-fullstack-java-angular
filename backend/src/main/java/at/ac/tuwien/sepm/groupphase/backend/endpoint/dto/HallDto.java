package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HallDto", description = "A DTO for a hall via rest")
public class HallDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The version of the hall")
  private int version;

  @ApiModelProperty(required = true, name = "The name of the hall")
  private String name;

  @ApiModelProperty(required = true, name = "The location of the hall")
  private LocationDto location;

  @JsonProperty("boundaryPoint")
  @ApiModelProperty(
      required = true,
      name = "The boundaryPoint which defines the maximum size of the hall")
  private PointDto boundaryPoint;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocationDto getLocation() {
    return location;
  }

  public void setLocation(LocationDto location) {
    this.location = location;
  }

  public PointDto getBoundaryPoint() {
    return boundaryPoint;
  }

  public void setBoundaryPoint(PointDto boundaryPoint) {
    this.boundaryPoint = boundaryPoint;
  }

  /**
   * Build the hall dto.
   */
  public HallDto build() {
    HallDto hallDto = new HallDto();
    hallDto.setId(id);
    hallDto.setBoundaryPoint(boundaryPoint);
    hallDto.setName(name);
    hallDto.setVersion(version);
    return hallDto;
  }
}
