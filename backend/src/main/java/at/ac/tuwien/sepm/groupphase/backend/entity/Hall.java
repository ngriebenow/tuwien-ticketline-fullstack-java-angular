package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Hall {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_event_id")
  @SequenceGenerator(name = "seq_event_id", sequenceName = "seq_event_id")
  private long id;

  @Column(nullable = false)
  private int version;

  @Column(nullable = false)
  private String name;

  @Embedded
  private Point boundaryPoint;

  //private Location location;

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  /*
  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }*/
}
