package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory.Builder;
import java.awt.Color;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class PriceCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_price_category_id")
  @SequenceGenerator(name = "seq_price_category_id", sequenceName = "seq_price_category_id")
  private Long id;

  @Column(nullable = false)
  private Color color;

  @Column(nullable = false)
  private int priceInCents;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Event event;

  public PriceCategory() {}

  private PriceCategory(Builder builder) {
    setId(builder.id);
    setColor(builder.color);
    setPriceInCents(builder.priceInCents);
    setName(builder.name);
    setEvent(builder.event);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PriceCategory that = (PriceCategory) o;
    return priceInCents == that.priceInCents
        && Objects.equals(id, that.id)
        && Objects.equals(color, that.color)
        && Objects.equals(name, that.name)
        && Objects.equals(event, that.event);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, color, priceInCents, name, event);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getPriceInCents() {
    return priceInCents;
  }

  public void setPriceInCents(int priceInCents) {
    this.priceInCents = priceInCents;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public static final class Builder {

    private Long id;
    private Color color;
    private int priceInCents;
    private String name;
    private Event event;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder color(Color val) {
      color = val;
      return this;
    }

    public Builder priceInCents(int val) {
      priceInCents = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder event(Event val) {
      event = val;
      return this;
    }

    public PriceCategory build() {
      return new PriceCategory(this);
    }
  }
}
