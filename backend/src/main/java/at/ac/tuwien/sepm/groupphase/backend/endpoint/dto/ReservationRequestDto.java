package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel("A request to buy or reserve one or more tickets")
public class ReservationRequestDto {

  @ApiModelProperty("The id of the performance to but the tickets for")
  private Long performanceId;

  @ApiModelProperty("The id of the client to but the tickets for")
  private Long clientId;

  @ApiModelProperty("A list of units ids with the amount to buy or reserve for each")
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

  public void setTicketRequestDtos(
      List<TicketRequestDto> ticketRequestDtos) {
    this.ticketRequestDtos = ticketRequestDtos;
  }
}
