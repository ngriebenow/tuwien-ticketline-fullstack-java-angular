package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "News")
public class News {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_news_id")
  @SequenceGenerator(name = "seq_news_id", sequenceName = "seq_news_id")
  private Long id;

  @Column(nullable = false)
  private LocalDateTime publishedAt;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String summary;

  @Column(nullable = false, length = 10_000)
  private String text;

  public static Builder builder() {
    return new Builder();
  }

  /** Construct the news entry. */
  public News() {}

  private News(Builder builder) {
    setId(builder.id);
    setPublishedAt(builder.publishedAt);
    setTitle(builder.title);
    setSummary(builder.summary);
    setText(builder.text);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    News news = (News) o;
    return Objects.equals(id, news.id)
        && Objects.equals(publishedAt, news.publishedAt)
        && Objects.equals(title, news.title)
        && Objects.equals(summary, news.summary)
        && Objects.equals(text, news.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publishedAt, title, summary, text);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(LocalDateTime publishedAt) {
    this.publishedAt = publishedAt;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public static final class Builder {

    private Long id;
    private LocalDateTime publishedAt;
    private String title;
    private String summary;
    private String text;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder publishedAt(LocalDateTime val) {
      publishedAt = val;
      return this;
    }

    public Builder title(String val) {
      title = val;
      return this;
    }

    public Builder summary(String val) {
      summary = val;
      return this;
    }

    public Builder text(String val) {
      text = val;
      return this;
    }

    public News build() {
      return new News(this);
    }
  }
}
