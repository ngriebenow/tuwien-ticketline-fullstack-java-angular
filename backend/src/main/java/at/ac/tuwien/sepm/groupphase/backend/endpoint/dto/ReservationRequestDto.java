package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(
    value = "ReservationRequest",
    description = "A request to buy or reserve one or more tickets")
public class ReservationRequestDto {

  @NotEmpty(message = "ticketRequests has to be set and not empty")
  @ApiModelProperty(
      value = "A list of units ids with the amount to buy or reserve for each",
      required = true)
  // TODO: require valid ticketRequests
  public List<@Valid TicketRequestDto> ticketRequests;

  @NotNull(message = "performanceId must be set")
  @ApiModelProperty(value = "The id of the performance to buy the tickets for", required = true)
  private Long performanceId;

  @NotNull(message = "clientId must be set")
  @ApiModelProperty(value = "The id of the client to buy the tickets for", required = true)
  private Long clientId;

  public ReservationRequestDto() {
  }

  private ReservationRequestDto(Builder builder) {
    setPerformanceId(builder.performanceId);
    setClientId(builder.clientId);
    setTicketRequests(builder.ticketRequests);
  }

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

  public List<TicketRequestDto> getTicketRequests() {
    return ticketRequests;
  }

  public void setTicketRequests(List<TicketRequestDto> ticketRequests) {
    this.ticketRequests = ticketRequests;
  }

  public static final class Builder {

    private Long performanceId;
    private Long clientId;
    private List<TicketRequestDto> ticketRequests;

    public Builder() {
    }

    public Builder performanceId(Long val) {
      performanceId = val;
      return this;
    }

    public Builder clientId(Long val) {
      clientId = val;
      return this;
    }

    public Builder ticketRequests(List<TicketRequestDto> val) {
      ticketRequests = val;
      return this;
    }

    public ReservationRequestDto build() {
      return new ReservationRequestDto(this);
    }
  }
}
