package at.ac.tuwien.sepm.groupphase.backend.entity;

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
public class Picture {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_picture_id")
  @SequenceGenerator(name = "seq_picture_id", sequenceName = "seq_picture_id")
  private Long id;

  @Column(nullable = false)
  private byte[] data;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private News news;

  /** Construct the picture. */
  public Picture() {}

  private Picture(Builder builder) {
    setId(builder.id);
    setData(builder.data);
    setNews(builder.news);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Picture picture = (Picture) o;
    return Objects.equals(id, picture.id) && Objects.equals(data, picture.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, data);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public News getNews() {
    return news;
  }

  public void setNews(News news) {
    this.news = news;
  }

  public static final class Builder {

    private Long id;
    private byte[] data;
    private News news;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder data(byte[] val) {
      data = val;
      return this;
    }

    public Builder news(News val) {
      news = val;
      return this;
    }

    public Picture build() {
      return new Picture(this);
    }
  }
}
