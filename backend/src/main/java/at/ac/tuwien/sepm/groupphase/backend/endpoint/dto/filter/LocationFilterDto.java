package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

import java.util.Objects;

public class LocationFilterDto {

  private String name;
  private String street;
  private String postalCode;
  private String place;
  private String country;

  /**
   * Constructor.
   */
  public LocationFilterDto(Builder builder) {
    setName(builder.name);
    setStreet(builder.street);
    setPostalCode(builder.postalCode);
    setPlace(builder.place);
    setCountry(builder.country);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationFilterDto that = (LocationFilterDto) o;
    return Objects.equals(name, that.name)
        && Objects.equals(street, that.street)
        && Objects.equals(postalCode, that.postalCode)
        && Objects.equals(place, that.place)
        && Objects.equals(country, that.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, street, postalCode, place, country);
  }


  public static final class Builder {

    private String name;
    private String street;
    private String postalCode;
    private String place;
    private String country;

    public Builder() {
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder street(String val) {
      street = val;
      return this;
    }

    public Builder postalCode(String val) {
      postalCode = val;
      return this;
    }

    public Builder place(String val) {
      place = val;
      return this;
    }

    public Builder country(String val) {
      country = val;
      return this;
    }

    public LocationFilterDto build() {
      return new LocationFilterDto(this);
    }
  }
}
