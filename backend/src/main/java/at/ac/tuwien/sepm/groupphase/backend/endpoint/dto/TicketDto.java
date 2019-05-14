package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TicketDto {

  @ApiModelProperty(readOnly = true, value = "The unique identifier of a specific ticket")
  private Long id;

  @ApiModelProperty(value = "This price of the ticket in european cents")
  private int priceInCents;

  @ApiModelProperty(value = "The name of the seat or category the ticket is for")
  private String unitName;

  @ApiModelProperty(value = "The id of the invoice that bought or reserved this ticket")
  private Long invoiceId;

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

  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public Long getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(Long invoiceId) {
    this.invoiceId = invoiceId;
  }
}
