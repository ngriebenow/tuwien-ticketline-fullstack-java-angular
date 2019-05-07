package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Performance {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_hall_id")
  @SequenceGenerator(name = "seq_hall_id", sequenceName = "seq_hall_id")
  private long id;

  @Column(nullable = false)
  private LocalDateTime startAt;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Event event;

  /** Construct the event. */
  public Performance(long id, LocalDateTime startAt, String name, Event event) {
    this.id = id;
    this.startAt = startAt;
    this.name = name;
    this.event = event;
  }

  public Performance() {}

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  /** Build the performance */
  public Performance build() {
    Performance performance = new Performance();
    performance.setId(id);
    performance.setEvent(event);
    performance.setName(name);
    performance.setStartAt(startAt);
    return performance;
  }
}
