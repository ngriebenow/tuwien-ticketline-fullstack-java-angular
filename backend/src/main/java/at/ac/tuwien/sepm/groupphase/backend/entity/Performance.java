package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
  private Long id;

  @Column(nullable = false)
  private LocalDateTime startAt;

  @Column(nullable = false)
  private LocalDateTime startAtUtc;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Event event;

  /**
   * Construct the event.
   */
  public Performance() {
  }

  public Performance(Builder builder) {
    setId(builder.id);
    setStartAt(builder.startAt);
    setStartAtUtc(builder.startAtUtc);
    setName(builder.name);
    setEvent(builder.event);
  }

  public static Builder newBuilder() {
    return new Builder();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Performance that = (Performance) o;

    boolean datesEqual = false;
    if (startAt != null && that.startAt != null) {
      datesEqual = Duration.between(startAt, that.startAt).abs().getSeconds() < 1;
    } else if (startAt != null || that.startAt != null) {
      return false;
    }
    return Objects.equals(id, that.id)
        && datesEqual
        && Objects.equals(name, that.name)
        && Objects.equals(event, that.event);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, startAt, name, event);
  }

  public LocalDateTime getStartAtUtc() {
    return startAtUtc;
  }

  public void setStartAtUtc(LocalDateTime startAtUtc) {
    this.startAtUtc = startAtUtc;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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


  public static final class Builder {

    private Long id;
    private LocalDateTime startAt;
    private LocalDateTime startAtUtc;
    private String name;
    private Event event;

    public Builder() {
    }

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder startAt(LocalDateTime val) {
      startAtUtc = val;
      return this;
    }

    /** Javadoc.*/
    public Builder startAtLocalAndUtc(LocalDateTime val) {
      startAt = val;
      startAtUtc = val;
      return this;
    }

    public Builder startAtUtc(LocalDateTime val) {
      startAtUtc = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder event(Event val) {
      event = val;
      return this;
    }

    public Performance build() {
      return new Performance(this);
    }
  }
}
