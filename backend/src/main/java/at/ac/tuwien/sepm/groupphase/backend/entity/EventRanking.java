package at.ac.tuwien.sepm.groupphase.backend.entity;

public class EventRanking {

  private Long soldTickets;

  private Long eventId;

  private String eventName;

  /** Build an event ranking by sold tickets. */
  public EventRanking(Long soldTickets, Long eventId, String eventName) {
    this.soldTickets = soldTickets;
    this.eventId = eventId;
    this.eventName = eventName;
  }

  public EventRanking() {
  }

  public Long getSoldTickets() {
    return soldTickets;
  }

  public void setSoldTickets(Long soldTickets) {
    this.soldTickets = soldTickets;
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
}
