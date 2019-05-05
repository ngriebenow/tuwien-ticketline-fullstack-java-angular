package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class HallDto {

  private long id;

  private int version;

  private String name;

  private LocationDto location;

  private PointDto boundaryPoint;

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

  public LocationDto getLocation() {
    return location;
  }

  public void setLocation(LocationDto location) {
    this.location = location;
  }

  public PointDto getBoundaryPoint() {
    return boundaryPoint;
  }

  public void setBoundaryPoint(PointDto boundaryPoint) {
    this.boundaryPoint = boundaryPoint;
  }
}
