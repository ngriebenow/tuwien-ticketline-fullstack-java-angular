package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;

@ApiModel(
    value = "Invoice",
    description = "An invoice at least one ticket and exactly one performance")
public class InvoiceDto {

  @ApiModelProperty("The unique identifier for an individual of this resource")
  private Long id;

  @ApiModelProperty("Whether this invoice has been paid")
  private boolean isPaid;

  @ApiModelProperty("Whether this invoice has been canceled")
  private boolean isCancelled;

  @ApiModelProperty("The reservation code of this invoice")
  private String reservationCode;

  @ApiModelProperty("The sequential number of this invoice")
  private Long number;

  @ApiModelProperty("The date this invoice was payed")
  private LocalDate paidAt;

  @ApiModelProperty("The id of the client who issued this invoice")
  private Long clientId;

  // TODO: Implement as soon as michis user persistence part is done
  // private Long selleId;

  @ApiModelProperty(value = "The tickets reserved and bought by this invoice")
  private List<TicketDto> tickets;

  public InvoiceDto() {}

  private InvoiceDto(Builder builder) {
    setId(builder.id);
    setPaid(builder.isPaid);
    setCancelled(builder.isCancelled);
    setReservationCode(builder.reservationCode);
    setNumber(builder.number);
    setPaidAt(builder.paidAt);
    setClientId(builder.clientId);
    setTickets(builder.tickets);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  public void setCancelled(boolean cancelled) {
    isCancelled = cancelled;
  }

  public String getReservationCode() {
    return reservationCode;
  }

  public void setReservationCode(String reservationCode) {
    this.reservationCode = reservationCode;
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public LocalDate getPaidAt() {
    return paidAt;
  }

  public void setPaidAt(LocalDate paidAt) {
    this.paidAt = paidAt;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public List<TicketDto> getTickets() {
    return tickets;
  }

  public void setTickets(List<TicketDto> tickets) {
    this.tickets = tickets;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvoiceDto that = (InvoiceDto) o;
    return isPaid == that.isPaid
        && isCancelled == that.isCancelled
        && Objects.equal(id, that.id)
        && Objects.equal(reservationCode, that.reservationCode)
        && Objects.equal(number, that.number)
        && Objects.equal(paidAt, that.paidAt)
        && Objects.equal(clientId, that.clientId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, isPaid, isCancelled, reservationCode, number, paidAt, clientId);
  }

  @Override
  public String toString() {
    return "InvoiceDto{"
        + "id="
        + id
        + ", isPaid="
        + isPaid
        + ", isCancelled="
        + isCancelled
        + ", reservationCode='"
        + reservationCode
        + '\''
        + ", number="
        + number
        + ", paidAt="
        + paidAt
        + ", clientId="
        + clientId
        + '}';
  }

  public static final class Builder {

    private Long id;
    private boolean isPaid;
    private boolean isCancelled;
    private String reservationCode;
    private Long number;
    private LocalDate paidAt;
    private Long clientId;
    private List<TicketDto> tickets;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder isPaid(boolean val) {
      isPaid = val;
      return this;
    }

    public Builder isCancelled(boolean val) {
      isCancelled = val;
      return this;
    }

    public Builder reservationCode(String val) {
      reservationCode = val;
      return this;
    }

    public Builder number(Long val) {
      number = val;
      return this;
    }

    public Builder paidAt(LocalDate val) {
      paidAt = val;
      return this;
    }

    public Builder clientId(Long val) {
      clientId = val;
      return this;
    }

    public Builder tickets(List<TicketDto> val) {
      tickets = val;
      return this;
    }

    public InvoiceDto build() {
      return new InvoiceDto(this);
    }
  }
}
