package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_artist_id")
  @SequenceGenerator(name = "seq_artist_id", sequenceName = "seq_artist_id")
  private long id;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "artists")
  private List<Event> events;

  public Artist(String surname, String name,
      List<Event> events) {
    this.surname = surname;
    this.name = name;
    this.events = events;
  }

  public Artist() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }

  /** Build the artist. */
  public Artist build() {
    Artist artist = new Artist();

    artist.setId(id);
    artist.setName(name);
    artist.setSurname(surname);
    artist.setEvents(events);

    return artist;
  }
}
