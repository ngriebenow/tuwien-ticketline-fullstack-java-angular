package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

@Entity
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_event_id")
  @SequenceGenerator(name = "seq_event_id", sequenceName = "seq_event_id")
  private Long id;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.ORDINAL)
  private EventCategory category;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Duration duration;

  @ManyToOne(optional = false)
  @JoinColumn(nullable = false)
  private Hall hall;

  @ManyToMany
  @JoinTable
  @Size(min = 1)
  private List<Artist> artists;

  /** Construct the event. */
  public Event() {}

  private Event(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setCategory(builder.category);
    setContent(builder.content);
    setDuration(builder.duration);
    setHall(builder.hall);
    setArtists(builder.artists);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(id, event.id) &&
        Objects.equals(name, event.name) &&
        category == event.category &&
        Objects.equals(content, event.content) &&
        Objects.equals(duration, event.duration) &&
        Objects.equals(hall, event.hall) &&
        Objects.equals(artists, event.artists);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, category, content, duration, hall, artists);
  }

  public List<Artist> getArtists() {
    return artists;
  }

  public void setArtists(List<Artist> artists) {
    this.artists = artists;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventCategory getCategory() {
    return category;
  }

  public void setCategory(EventCategory category) {
    this.category = category;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public Hall getHall() {
    return hall;
  }

  public void setHall(Hall hall) {
    this.hall = hall;
  }

  public static final class Builder {

    private Long id;
    private String name;
    private EventCategory category;
    private String content;
    private Duration duration;
    private Hall hall;
    private @Size(min = 1) List<Artist> artists;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder category(EventCategory val) {
      category = val;
      return this;
    }

    public Builder content(String val) {
      content = val;
      return this;
    }

    public Builder duration(Duration val) {
      duration = val;
      return this;
    }

    public Builder hall(Hall val) {
      hall = val;
      return this;
    }

    public Builder artists(@Size(min = 1) List<Artist> val) {
      artists = val;
      return this;
    }

    public Event build() {
      return new Event(this);
    }
  }
}
