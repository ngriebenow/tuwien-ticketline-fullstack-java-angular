package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.awt.Color;

public class PriceCategoryDto {

  private long id;

  private int priceInCent;

  private String name;

  private Color color;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getPriceInCent() {
    return priceInCent;
  }

  public void setPriceInCent(int priceInCent) {
    this.priceInCent = priceInCent;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
