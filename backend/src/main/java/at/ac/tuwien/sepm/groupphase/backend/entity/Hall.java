package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Hall {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_hall_id")
  @SequenceGenerator(name = "seq_hall_id", sequenceName = "seq_hall_id")
  private Long id;

  @Column(nullable = false)
  private int version;

  @Column(nullable = false)
  private String name;

  @Embedded private Point boundaryPoint;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Location location;

  public Hall() {}

  /** Construct the event. */
  private Hall(Builder builder) {
    setId(builder.id);
    setVersion(builder.version);
    setName(builder.name);
    setBoundaryPoint(builder.boundaryPoint);
    setLocation(builder.location);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Hall hall = (Hall) o;
    return version == hall.version
        && Objects.equals(id, hall.id)
        && Objects.equals(name, hall.name)
        && Objects.equals(boundaryPoint, hall.boundaryPoint)
        && Objects.equals(location, hall.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, name, boundaryPoint, location);
  }

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

  public Point getBoundaryPoint() {
    return boundaryPoint;
  }

  public void setBoundaryPoint(Point boundaryPoint) {
    this.boundaryPoint = boundaryPoint;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public static final class Builder {

    private Long id;
    private int version;
    private String name;
    private Point boundaryPoint;
    private Location location;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder version(int val) {
      version = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder boundaryPoint(Point val) {
      boundaryPoint = val;
      return this;
    }

    public Builder location(Location val) {
      location = val;
      return this;
    }

    public Hall build() {
      return new Hall(this);
    }
  }
}
