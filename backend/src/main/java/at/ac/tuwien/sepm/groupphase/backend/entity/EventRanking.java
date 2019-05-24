package at.ac.tuwien.sepm.groupphase.backend.entity;

public class EventRanking {

  private Long count;

  private Long eventId;

  private String eventName;

  public EventRanking(Long count, Long eventId, String eventName) {
    this.count = count;
    this.eventId = eventId;
    this.eventName = eventName;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
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
