package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

@ApiModel(value = "DefinedUnitDto", description = "A DTO for a defined unit via rest")
public class DefinedUnitDto {

  @ApiModelProperty(required = true, name = "The name of the defined unit")
  private String name;

  @ApiModelProperty(required = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The free capacities of the defined unit")
  private int free;

  @ApiModelProperty(required = true, name = "The overall capacity of the defined unit")
  private int capacity;

  @ApiModelProperty(required = true, name = "The upper bound of the defined unit")
  private PointDto lowerBoundary;

  @ApiModelProperty(required = true, name = "The lower bound of the defined unit")
  private PointDto upperBoundary;

  @ApiModelProperty(required = true, name = "The id of the price category")
  private Long priceCategoryId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getFree() {
    return free;
  }

  public void setFree(int free) {
    this.free = free;
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

  public Long getPriceCategoryId() {
    return priceCategoryId;
  }

  public void setPriceCategoryId(Long priceCategoryId) {
    this.priceCategoryId = priceCategoryId;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DefinedUnitDto build() {
    DefinedUnitDto dto = new DefinedUnitDto();
    dto.setId(id);
    dto.setName(name);
    dto.setFree(free);
    dto.setCapacity(capacity);
    dto.setLowerBoundary(lowerBoundary);
    dto.setUpperBoundary(upperBoundary);
    dto.setPriceCategoryId(priceCategoryId);
    return dto;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefinedUnitDto that = (DefinedUnitDto) o;
    return free == that.free &&
        capacity == that.capacity &&
        Objects.equals(name, that.name) &&
        Objects.equals(id, that.id) &&
        lowerBoundary.equals(that.lowerBoundary) &&
        upperBoundary.equals(that.upperBoundary) &&
        Objects.equals(priceCategoryId, that.priceCategoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, free, capacity, lowerBoundary, upperBoundary, priceCategoryId);
  }
}
