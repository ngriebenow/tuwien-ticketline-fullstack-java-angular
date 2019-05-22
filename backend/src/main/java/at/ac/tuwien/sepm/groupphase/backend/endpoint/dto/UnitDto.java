package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel(value = "UnitDto", description = "A DTO for a unit via rest")
public class UnitDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The database id of the hall this unit belongs to")
  private Long hallId;

  @NotBlank(message = "name must not be empty")
  @ApiModelProperty(required = true, name = "The name of the unit")
  private String name;

  @NotNull(message = "lowerBoundary must be set")
  @Valid
  @ApiModelProperty(required = true, name = "The lower right point of the unit")
  private PointDto lowerBoundary;

  @NotNull(message = "upperBoundary must be set")
  @Valid
  @ApiModelProperty(required = true, name = "The upper left point of the unit")
  private PointDto upperBoundary;

  @NotNull(message = "capacity must be set")
  @Positive(message = "capacity must be positive")
  @ApiModelProperty(required = true, name = "The capacity of the unit")
  private int capacity;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getHallId() {
    return hallId;
  }

  public void setHallId(Long hallId) {
    this.hallId = hallId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PointDto getLowerBoundary() {
    return lowerBoundary;
  }

  public void setLowerBoundary(PointDto lowerBoundary) {
    this.lowerBoundary = lowerBoundary;
  }

  public PointDto getUpperBoundary() {
    return upperBoundary;
  }

  public void setUpperBoundary(PointDto upperBoundary) {
    this.upperBoundary = upperBoundary;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
}
