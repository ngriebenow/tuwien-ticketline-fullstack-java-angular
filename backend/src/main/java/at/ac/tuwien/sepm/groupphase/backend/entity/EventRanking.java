package at.ac.tuwien.sepm.groupphase.backend.entity;

public class EventRanking {

  private Integer count;
  private Event event;

  public EventRanking(Integer count, Event event) {
    this.count = count;
    this.event = event;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}
