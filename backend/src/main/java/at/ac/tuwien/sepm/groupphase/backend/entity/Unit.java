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

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_unit_id")
  @SequenceGenerator(name = "seq_unit_id", sequenceName = "seq_unit_id")
  private long id;

  @Column(nullable = false)
  private String name;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "coordinateX", column = @Column(name = "lower_coordinatex")),
      @AttributeOverride(name = "coordinateY", column = @Column(name = "lower_coordinatey"))
  })
  private Point lowerBoundary;

  @AttributeOverrides({
      @AttributeOverride(name = "coordinateX", column = @Column(name = "upper_coordinatex")),
      @AttributeOverride(name = "coordinateY", column = @Column(name = "upper_coordinatey"))
  })
  private Point upperBoundary;

  @Column(nullable = false)
  private int capacity;

  // TODO: Evaluate why/if nulls are still possible and write a test for it
  @ManyToOne(optional = false)
  @JoinColumn(nullable = false)
  private Hall hall;

  private Unit(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setLowerBoundary(builder.lowerBoundary);
    setUpperBoundary(builder.upperBoundary);
    setCapacity(builder.capacity);
    setHall(builder.hall);
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Unit unit = (Unit) o;
    return id == unit.id
        && capacity == unit.capacity
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

    private long id;
    private String name;
    private Point lowerBoundary;
    private Point upperBoundary;
    private int capacity;
    private Hall hall;

    public Builder() {}

    public Builder id(long val) {
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
