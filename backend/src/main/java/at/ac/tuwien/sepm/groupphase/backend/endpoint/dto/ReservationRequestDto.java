package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(
    value = "ReservationRequest",
    description = "A request to buy or reserve one or more tickets")
public class ReservationRequestDto {

  @ApiModelProperty(value = "The id of the performance to buy the tickets for", required = true)
  private Long performanceId;

  @ApiModelProperty(value = "The id of the client to buy the tickets for", required = true)
  private Long clientId;

  @ApiModelProperty(
      value = "A list of units ids with the amount to buy or reserve for each",
      required = true)
  public List<TicketRequestDto> ticketRequestDtos;

  public Long getPerformanceId() {
    return performanceId;
  }

  public void setPerformanceId(Long performanceId) {
    this.performanceId = performanceId;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public List<TicketRequestDto> getTicketRequestDtos() {
    return ticketRequestDtos;
  }

  public void setTicketRequestDtos(List<TicketRequestDto> ticketRequestDtos) {
    this.ticketRequestDtos = ticketRequestDtos;
  }
}
