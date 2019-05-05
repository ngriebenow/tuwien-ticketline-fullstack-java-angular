package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;

public class PerformanceDto {

  private long id;

  private LocalDateTime startAt;

  private EventDto event;

  private String name = "[eventName] [Location] [Time]";

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDateTime getStartAt() {
    return startAt;
  }

  public void setStartAt(LocalDateTime startAt) {
    this.startAt = startAt;
  }

  public EventDto getEvent() {
    return event;
  }

  public void setEvent(EventDto event) {
    this.event = event;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
