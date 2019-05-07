package at.ac.tuwien.sepm.groupphase.backend.entity;

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
  private long id;

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

  public Location(long id, String name, String street, String postalCode, String place,
      String country) {
    this.id = id;
    this.name = name;
    this.street = street;
    this.postalCode = postalCode;
    this.place = place;
    this.country = country;
  }

  public Location() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  /** Build the event. */
  public Location build() {
    Location location = new Location();
    location.setCountry(country);
    location.setId(id);
    location.setName(name);
    location.setStreet(street);
    location.setPostalCode(postalCode);
    return location;
  }
}
