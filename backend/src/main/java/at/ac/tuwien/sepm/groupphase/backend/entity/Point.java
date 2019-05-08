package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Point {

  private int coordinateX;

  private int coordinateY;

  public Point() {}

  private Point(Builder builder) {
    setCoordinateX(builder.coordinateX);
    setCoordinateY(builder.coordinateY);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Point point = (Point) o;
    return coordinateX == point.coordinateX && coordinateY == point.coordinateY;
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordinateX, coordinateY);
  }

  public int getCoordinateX() {
    return coordinateX;
  }

  public void setCoordinateX(int coordinateX) {
    this.coordinateX = coordinateX;
  }

  public int getCoordinateY() {
    return coordinateY;
  }

  public void setCoordinateY(int coordinateY) {
    this.coordinateY = coordinateY;
  }

  public static final class Builder {

    private int coordinateX;
    private int coordinateY;

    public Builder() {}

    public Builder coordinateX(int val) {
      coordinateX = val;
      return this;
    }

    public Builder coordinateY(int val) {
      coordinateY = val;
      return this;
    }

    public Point build() {
      return new Point(this);
    }
  }
}
