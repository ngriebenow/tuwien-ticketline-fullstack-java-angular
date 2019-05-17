package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel
public class InvoiceDto {

  @ApiModelProperty(value = "The unique identifier for an individual of this resource")
  private Long id;

  @ApiModelProperty(value = "Whether this invoice has been paid")
  private boolean isPaid;

  @ApiModelProperty(value = "Whether this invoice has been canceled")
  private boolean isCancelled;

  @ApiModelProperty(value = "The reservation code of this invoice")
  private String reservationCode;

  @ApiModelProperty(value = "The id of the client who issued this invoice")
  private Long clientId;

  // TODO: Implement as soon as michis user persistance part is done
  // private Long selleId;

  @ApiModelProperty(value = "The tickets reserved and bought by this invoice")
  private List<TicketDto> tickets;

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
}
