package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class DefinedUnit {

  private static final String ID_SEQUENCE_NAME = "seq_defined_unit_id";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
  @SequenceGenerator(name = ID_SEQUENCE_NAME)
  private Long id;

  @Column(nullable = false)
  private int capacityFree;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Unit unit;

  @ManyToOne
  @JoinColumn(nullable = false)
  private PriceCategory priceCategory;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Performance performance;

  public DefinedUnit() {}

  private DefinedUnit(Builder builder) {
    setId(builder.id);
    setCapacityFree(builder.capacityFree);
    setUnit(builder.unit);
    setPriceCategory(builder.priceCategory);
    setPerformance(builder.performance);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getCapacityFree() {
    return capacityFree;
  }

  public void setCapacityFree(int capacityFree) {
    this.capacityFree = capacityFree;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public PriceCategory getPriceCategory() {
    return priceCategory;
  }

  public void setPriceCategory(PriceCategory priceCategory) {
    this.priceCategory = priceCategory;
  }

  public Performance getPerformance() {
    return performance;
  }

  public void setPerformance(Performance performance) {
    this.performance = performance;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DefinedUnit that = (DefinedUnit) obj;
    return capacityFree == that.capacityFree
        && Objects.equal(id, that.id)
        && Objects.equal(unit, that.unit)
        && Objects.equal(priceCategory, that.priceCategory)
        && Objects.equal(performance, that.performance);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, capacityFree, unit, priceCategory, performance);
  }

  @Override
  public String toString() {
    return "DefinedUnit{"
        + "id="
        + id
        + ", capacityFree="
        + capacityFree
        + ", unit="
        + unit
        + ", priceCategory="
        + priceCategory
        + ", performance="
        + performance
        + '}';
  }

  public static final class Builder {

    private Long id;
    private int capacityFree;
    private Unit unit;
    private PriceCategory priceCategory;
    private Performance performance;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder capacityFree(int val) {
      capacityFree = val;
      return this;
    }

    public Builder unit(Unit val) {
      unit = val;
      return this;
    }

    public Builder priceCategory(PriceCategory val) {
      priceCategory = val;
      return this;
    }

    public Builder performance(Performance val) {
      performance = val;
      return this;
    }

    public DefinedUnit build() {
      return new DefinedUnit(this);
    }
  }
}
