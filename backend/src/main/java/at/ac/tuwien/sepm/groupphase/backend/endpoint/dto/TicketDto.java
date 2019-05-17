package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Ticket", description = "A ticket to a performance")
public class TicketDto {

  @ApiModelProperty("The unique identifier of a specific ticket")
  private Long id;

  @ApiModelProperty("This price of the ticket in european cents")
  private int priceInCents;

  @ApiModelProperty("The name of the seat or category the ticket is for")
  private String title;

  @ApiModelProperty("The id of the invoice that bought or reserved this ticket")
  private Long invoiceId;

  @ApiModelProperty("The Id of the performance the ticket is for")
  private Long performanceId;

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

  public Long getPerformanceId() {
    return performanceId;
  }

  public void setPerformanceId(Long performanceId) {
    this.performanceId = performanceId;
  }
}
