package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Point {

  private int coordinateX;

  private int coordinateY;

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

  /** Build the point. */
  public Point build() {
    Point point = new Point();
    point.setCoordinateX(coordinateX);
    point.setCoordinateY(coordinateY);
    return point;
  }
}
