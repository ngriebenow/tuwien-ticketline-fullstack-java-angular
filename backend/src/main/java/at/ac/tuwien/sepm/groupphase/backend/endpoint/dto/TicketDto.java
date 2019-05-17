package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.google.common.base.Objects;
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

  public TicketDto() {}

  private TicketDto(Builder builder) {
    setId(builder.id);
    setPriceInCents(builder.priceInCents);
    setTitle(builder.title);
    setInvoiceId(builder.invoiceId);
    setPerformanceId(builder.performanceId);
  }

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    TicketDto ticketDto = (TicketDto) obj;
    return priceInCents == ticketDto.priceInCents
        && Objects.equal(id, ticketDto.id)
        && Objects.equal(title, ticketDto.title)
        && Objects.equal(invoiceId, ticketDto.invoiceId)
        && Objects.equal(performanceId, ticketDto.performanceId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, priceInCents, title, invoiceId, performanceId);
  }

  @Override
  public String toString() {
    return "TicketDto{"
        + "id="
        + id
        + ", priceInCents="
        + priceInCents
        + ", title='"
        + title
        + '\''
        + ", invoiceId="
        + invoiceId
        + ", performanceId="
        + performanceId
        + '}';
  }

  public static final class Builder {

    private Long id;
    private int priceInCents;
    private String title;
    private Long invoiceId;
    private Long performanceId;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder priceInCents(int val) {
      priceInCents = val;
      return this;
    }

    public Builder title(String val) {
      title = val;
      return this;
    }

    public Builder invoiceId(Long val) {
      invoiceId = val;
      return this;
    }

    public Builder performanceId(Long val) {
      performanceId = val;
      return this;
    }

    public TicketDto build() {
      return new TicketDto(this);
    }
  }
}
