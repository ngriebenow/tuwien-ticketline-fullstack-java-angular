package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Objects;

@ApiModel(value = "EventSearchResultDto", description = "A DTO for an event via rest")
public class EventSearchResultDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The name of the event")
  private String name;

  @ApiModelProperty(required = true, name = "The category of the event")
  private EventCategory category;

  @ApiModelProperty(required = true, name = "The hall name in which the event takes place")
  private String hallName;

  @ApiModelProperty(required = true, name = "The location name in which the event takes place")
  private String locationName;

  @ApiModelProperty(required = true, name = "The location place in which the event takes place")
  private String locationPlace;

  @ApiModelProperty(required = true, name = "The price range of the event")
  private String priceRange;

  @ApiModelProperty(required = true, name = "The corresponding performances")
  private List<PerformanceSearchResultDto> performances;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventCategory getCategory() {
    return category;
  }

  public void setCategory(EventCategory category) {
    this.category = category;
  }

  public String getHallName() {
    return hallName;
  }

  public void setHallName(String hallName) {
    this.hallName = hallName;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public String getLocationPlace() {
    return locationPlace;
  }

  public void setLocationPlace(String locationPlace) {
    this.locationPlace = locationPlace;
  }

  public String getPriceRange() {
    return priceRange;
  }

  public void setPriceRange(String priceRange) {
    this.priceRange = priceRange;
  }

  public List<PerformanceSearchResultDto> getPerformances() {
    return performances;
  }

  public void setPerformances(List<PerformanceSearchResultDto> performances) {
    this.performances = performances;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventSearchResultDto that = (EventSearchResultDto) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name)
        && category == that.category
        && Objects.equals(hallName, that.hallName)
        && Objects.equals(locationName, that.locationName)
        && Objects.equals(locationPlace, that.locationPlace)
        && Objects.equals(priceRange, that.priceRange);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, name, category, hallName, locationName, locationPlace, priceRange, performances);
  }
}
