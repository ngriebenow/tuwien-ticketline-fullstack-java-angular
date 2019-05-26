package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sequence {

  @Id
  private String name;

  @Column(nullable = false)
  private Long nextValue;

  public Sequence() {
  }

  private Sequence(Builder builder) {
    setName(builder.name);
    setNextValue(builder.nextValue);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getNextValue() {
    return nextValue;
  }

  public void setNextValue(Long nextValue) {
    this.nextValue = nextValue;
  }

  public static final class Builder {

    private String name;
    private Long nextValue;

    public Builder() {
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder nextValue(Long val) {
      nextValue = val;
      return this;
    }

    public Sequence build() {
      return new Sequence(this);
    }
  }
}
