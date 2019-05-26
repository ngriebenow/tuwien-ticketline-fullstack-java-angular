package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "LocationDto", description = "A DTO for a location via rest")
public class LocationDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The name of the location")
  private String name;

  @ApiModelProperty(required = true, name = "The street of the location")
  private String street;

  @ApiModelProperty(required = true, name = "The postal code of the location")
  private String postalCode;

  @ApiModelProperty(required = true, name = "The place of the location")
  private String place;

  @ApiModelProperty(required = true, name = "The country of the location")
  private String country;

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

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Build the location dto.
   */
  public LocationDto build() {
    LocationDto locationDto = new LocationDto();
    locationDto.setCountry(country);
    locationDto.setId(id);
    locationDto.setName(name);
    locationDto.setStreet(street);
    locationDto.setPostalCode(postalCode);
    return locationDto;
  }
}
