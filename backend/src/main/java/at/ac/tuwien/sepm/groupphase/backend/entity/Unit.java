package at.ac.tuwien.sepm.groupphase.backend.entity;

public class Unit {

  private long id;

  private String name;

  private Point lowerBoundary;

  private Point upperBoundary;

  private int capacity;

  private Hall hall;

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

  public Point getLowerBoundary() {
    return lowerBoundary;
  }

  public void setLowerBoundary(Point lowerBoundary) {
    this.lowerBoundary = lowerBoundary;
  }

  public Point getUpperBoundary() {
    return upperBoundary;
  }

  public void setUpperBoundary(Point upperBoundary) {
    this.upperBoundary = upperBoundary;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public Hall getHall() {
    return hall;
  }

  public void setHall(Hall hall) {
    this.hall = hall;
  }
}
