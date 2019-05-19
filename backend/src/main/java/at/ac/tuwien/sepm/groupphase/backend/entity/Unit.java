package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Unit {

  private static final String ID_SEUQUECE_NAME = "seq_unit_id";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEUQUECE_NAME)
  @SequenceGenerator(name = ID_SEUQUECE_NAME)
  private Long id;

  @Column(nullable = false)
  private String name;

  @AttributeOverrides({
      @AttributeOverride(
        name = "coordinateX",
        column = @Column(name = "lower_coordinatex", nullable = false)),
      @AttributeOverride(
        name = "coordinateY",
        column = @Column(name = "lower_coordinatey", nullable = false)),
  })
  @Embedded
  private Point lowerBoundary;

  @AttributeOverrides({
      @AttributeOverride(
        name = "coordinateX",
        column = @Column(name = "upper_coordinatex", nullable = false)),
      @AttributeOverride(
        name = "coordinateY",
        column = @Column(name = "upper_coordinatey", nullable = false)),
  })
  private Point upperBoundary;

  @Column(nullable = false)
  private int capacity;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Hall hall;

  public Unit() {}

  private Unit(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setLowerBoundary(builder.lowerBoundary);
    setUpperBoundary(builder.upperBoundary);
    setCapacity(builder.capacity);
    setHall(builder.hall);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Unit unit = (Unit) obj;
    return capacity == unit.capacity
        && Objects.equal(id, unit.id)
        && Objects.equal(name, unit.name)
        && Objects.equal(lowerBoundary, unit.lowerBoundary)
        && Objects.equal(upperBoundary, unit.upperBoundary)
        && Objects.equal(hall, unit.hall);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, name, lowerBoundary, upperBoundary, capacity, hall);
  }

  @Override
  public String toString() {
    return "Unit{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", lowerBoundary="
        + lowerBoundary
        + ", upperBoundary="
        + upperBoundary
        + ", capacity="
        + capacity
        + ", hall="
        + hall
        + '}';
  }

  public static final class Builder {

    private Long id;
    private String name;
    private Point lowerBoundary;
    private Point upperBoundary;
    private int capacity;
    private Hall hall;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder lowerBoundary(Point val) {
      lowerBoundary = val;
      return this;
    }

    public Builder upperBoundary(Point val) {
      upperBoundary = val;
      return this;
    }

    public Builder capacity(int val) {
      capacity = val;
      return this;
    }

    public Builder hall(Hall val) {
      hall = val;
      return this;
    }

    public Unit build() {
      return new Unit(this);
    }
  }
}
