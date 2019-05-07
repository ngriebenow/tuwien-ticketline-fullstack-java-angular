package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class UnitDto {

  private long id;

  private long hallId;

  private PointDto lowerBoundary;

  private PointDto upperBoundary;

  private int capacity;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getHallId() {
    return hallId;
  }

  public void setHallId(long hallId) {
    this.hallId = hallId;
  }

  public PointDto getLowerBoundary() {
    return lowerBoundary;
  }

  public void setLowerBoundary(PointDto lowerBoundary) {
    this.lowerBoundary = lowerBoundary;
  }

  public PointDto getUpperBoundary() {
    return upperBoundary;
  }

  public void setUpperBoundary(PointDto upperBoundary) {
    this.upperBoundary = upperBoundary;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
}
