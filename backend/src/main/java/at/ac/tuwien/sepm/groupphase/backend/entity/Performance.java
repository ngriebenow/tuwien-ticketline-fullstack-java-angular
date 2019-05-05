package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.LocalDateTime;

public class Performance {

  private long id;

  private LocalDateTime time;

  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
