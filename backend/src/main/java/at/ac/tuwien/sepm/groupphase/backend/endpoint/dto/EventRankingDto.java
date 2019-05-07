package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EventRankingDto", description = "A DTO for an event ranking via rest")
public class EventRankingDto {

  @ApiModelProperty(required = true, name = "The ranking of the event")
  private int rank;

  @ApiModelProperty(required = true, name = "The event")
  private EventDto event;

  @ApiModelProperty(required = true, name = "The tickets sold in total of the event")
  private int soldTickets;

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public EventDto getEvent() {
    return event;
  }

  public void setEvent(EventDto event) {
    this.event = event;
  }

  public int getSoldTickets() {
    return soldTickets;
  }

  public void setSoldTickets(int soldTickets) {
    this.soldTickets = soldTickets;
  }

  /** Build the event ranking dto. */
  public EventRankingDto build() {
    EventRankingDto eventRankingDto = new EventRankingDto();
    eventRankingDto.setRank(rank);
    eventRankingDto.setSoldTickets(soldTickets);
    eventRankingDto.setEvent(event);
    return eventRankingDto;
  }
}
