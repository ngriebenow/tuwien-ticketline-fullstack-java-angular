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
  private String title;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(Long invoiceId) {
    this.invoiceId = invoiceId;
  }
}
