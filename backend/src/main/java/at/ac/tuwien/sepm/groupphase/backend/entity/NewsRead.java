package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.Objects;
import javax.persistence.Column;

public class NewsRead {

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private Long newsId;

  /** Construct the newsRead instance. */
  public NewsRead() {}

  private NewsRead(Builder builder) {
    setUsername(builder.username);
    setNewsId(builder.newsId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewsRead newsRead = (NewsRead) o;
    return Objects.equals(username, newsRead.username)
        && Objects.equals(newsId, newsRead.newsId);

  }

  @Override
  public int hashCode() {
    return Objects.hash(username, newsId);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getNewsId() {
    return newsId;
  }

  public void setNewsId(Long newsId) {
    this.newsId = newsId;
  }

  public static final class Builder {

    private String username;
    private Long newsId;

    public Builder() {
    }

    public Builder username(String val) {
      username = val;
      return this;
    }

    public Builder newsId(Long val) {
      newsId = val;
      return this;
    }

    public NewsRead build() {
      return new NewsRead(this);
    }
  }
}
