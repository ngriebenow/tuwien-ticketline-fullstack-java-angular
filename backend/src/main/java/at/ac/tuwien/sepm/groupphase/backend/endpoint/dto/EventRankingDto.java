package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class EventRankingDto {

  private int rank;

  private EventDto event;

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
}
