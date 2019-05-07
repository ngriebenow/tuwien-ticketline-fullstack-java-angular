package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
  private long id;

  @Column(nullable = false)
  private int version;

  @Column(nullable = false)
  private String name;

  @Embedded
  private Point boundaryPoint;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Location location;


  public Hall() {}

  public Hall(long id, int version, String name,
      Point boundaryPoint, Location location) {
    this.id = id;
    this.version = version;
    this.name = name;
    this.boundaryPoint = boundaryPoint;
  }


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


  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  /** Build the hall. */
  public Hall build() {
    Hall hall = new Hall();
    hall.setId(id);
    hall.setBoundaryPoint(boundaryPoint);
    hall.setName(name);
    hall.setVersion(version);
    return hall;
  }
}
