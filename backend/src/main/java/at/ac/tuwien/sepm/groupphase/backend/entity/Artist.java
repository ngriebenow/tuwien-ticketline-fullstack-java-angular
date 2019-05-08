package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_artist_id")
  @SequenceGenerator(name = "seq_artist_id", sequenceName = "seq_artist_id")
  private Long id;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false)
  private String name;

  public Artist() {}

  private Artist(Builder builder) {
    setId(builder.id);
    setSurname(builder.surname);
    setName(builder.name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Artist artist = (Artist) o;
    return Objects.equals(id, artist.id)
        && Objects.equals(surname, artist.surname)
        && Objects.equals(name, artist.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, surname, name);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public static final class Builder {

    private Long id;
    private String surname;
    private String name;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder surname(String val) {
      surname = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Artist build() {
      return new Artist(this);
    }
  }
}
