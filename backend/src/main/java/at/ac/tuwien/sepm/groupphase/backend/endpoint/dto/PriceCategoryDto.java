package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.awt.Color;

@ApiModel(value = "PriceCategoryDto", description = "A DTO for a price category via rest")
public class PriceCategoryDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The price in cents of the price category")
  private int priceInCents;

  @ApiModelProperty(required = true, name = "The name of the category")
  private String name;

  @ApiModelProperty(readOnly = true, name = "The color of the category")
  private Color color;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getPriceInCents() {
    return priceInCents;
  }

  public void setPriceInCents(int priceInCents) {
    this.priceInCents = priceInCents;
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

  /** Build the PriceCategory dto. */
  public PriceCategoryDto build() {
    PriceCategoryDto priceCategoryDto = new PriceCategoryDto();
    priceCategoryDto.setColor(color);
    priceCategoryDto.setId(id);
    priceCategoryDto.setPriceInCents(priceInCents);
    return priceCategoryDto;
  }
}
