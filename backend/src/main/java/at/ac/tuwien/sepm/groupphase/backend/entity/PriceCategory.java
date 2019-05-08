package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.awt.Color;

public class PriceCategory {

  private long id;

  private Color color;

  private int priceInCents;

  private Event event;

  public long getId() {
    return id;
  }

  public void setId(long id) {
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
}
