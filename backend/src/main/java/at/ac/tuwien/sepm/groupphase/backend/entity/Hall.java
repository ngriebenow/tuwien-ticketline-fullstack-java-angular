package at.ac.tuwien.sepm.groupphase.backend.entity;

public class Hall {

  private long id;

  private int version;

  private String name;

  private Point boundaryPoint;

  private Location location;

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
}
