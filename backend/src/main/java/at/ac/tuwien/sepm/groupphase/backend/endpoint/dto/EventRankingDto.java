package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EventRankingDto", description = "A DTO for an event ranking via rest")
public class EventRankingDto {

  @ApiModelProperty(required = true, name = "The id of the event")
  private Long eventId;

  @ApiModelProperty(required = true, name = "The name of the event")
  private String eventName;

  @ApiModelProperty(required = true, name = "The tickets sold in total of the event")
  private Long soldTickets;

  public EventRankingDto() {
  }

  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public Long getSoldTickets() {
    return soldTickets;
  }

  public void setSoldTickets(Long soldTickets) {
    this.soldTickets = soldTickets;
  }
}
