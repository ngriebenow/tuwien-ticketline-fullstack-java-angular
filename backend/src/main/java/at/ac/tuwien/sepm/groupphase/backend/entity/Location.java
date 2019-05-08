package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_location_id")
  @SequenceGenerator(name = "seq_location_id", sequenceName = "seq_location_id")
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private String postalCode;

  @Column(nullable = false)
  private String place;

  @Column(nullable = false)
  private String country;

  /** Construct the event. */
  public Location() {}

  private Location(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setStreet(builder.street);
    setPostalCode(builder.postalCode);
    setPlace(builder.place);
    setCountry(builder.country);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Location location = (Location) o;
    return Objects.equals(id, location.id)
        && Objects.equals(name, location.name)
        && Objects.equals(street, location.street)
        && Objects.equals(postalCode, location.postalCode)
        && Objects.equals(place, location.place)
        && Objects.equals(country, location.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, street, postalCode, place, country);
  }

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

  public static final class Builder {

    private Long id;
    private String name;
    private String street;
    private String postalCode;
    private String place;
    private String country;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
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

    public Location build() {
      return new Location(this);
    }
  }
}
