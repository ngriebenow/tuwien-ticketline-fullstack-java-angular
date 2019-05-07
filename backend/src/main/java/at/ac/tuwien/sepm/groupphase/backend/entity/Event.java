package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.Duration;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_event_id")
  @SequenceGenerator(name = "seq_event_id", sequenceName = "seq_event_id")
  private long id;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.ORDINAL)
  private EventCategory category;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Duration duration;


  @ManyToOne(optional = false)
  private Hall hall;


  @ManyToMany
  @JoinTable
  private List<Artist> artists;


  public Event(long id, String name, EventCategory category, String content, Duration duration,
      Hall hall, List<Artist> artists) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.content = content;
    this.duration = duration;
    this.hall = hall;
    this.artists = artists;
  }

  public Event() {
  }

  public List<Artist> getArtists() {
    return artists;
  }

  public void setArtists(List<Artist> artists) {
    this.artists = artists;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  /** Build the event. */
  public Event build() {
    Event event = new Event();
    event.setId(id);
    event.setContent(content);
    event.setName(name);
    event.setCategory(category);
    event.setDuration(duration);
    return event;
  }

}
