package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.Duration;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Event {

  @Id
  private long id;

  @Column(nullable = false)
  private String name;

  //private EventCategory category;

  @Column(nullable = false)
  private String content;

  //@Column(nullable = false)
  //private Duration duration;


  //private Hall hall;


  //@ManyToMany
  //@JoinTable
  //private List<Artist> artists;




  /*public List<Artist> getArtists() {
    return artists;
  }

  public void setArtists(List<Artist> artists) {
    this.artists = artists;
  }*/

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

  /*
  public EventCategory getCategory() {
    return category;
  }

  public void setCategory(EventCategory category) {
    this.category = category;
  }*/

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  /*public Duration getDuration() {
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
  }*/

  public Event build() {
    Event event = new Event();
    event.setId(id);
    event.setContent(content);
    event.setName(name);
    return event;
  }

}
