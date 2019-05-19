package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "NewsRead")
@IdClass(UserNewsKey.class)
public class NewsRead {

  @Id
  @ManyToOne
  @JoinColumn
  private User user;

  @Id
  @ManyToOne
  @JoinColumn
  private News news;


  /** Construct the newsRead instance. */
  public NewsRead() {}

  private NewsRead(Builder builder) {
    setUser(builder.user);
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
    NewsRead newsRead = (NewsRead) o;
    return Objects.equals(user, newsRead.user) && Objects.equals(news, newsRead.news);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, news);
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public News getNews() {
    return news;
  }

  public void setNews(News news) {
    this.news = news;
  }

  public static final class Builder {

    private User user;
    private News news;

    public Builder() {}

    public Builder user(User val) {
      user = val;
      return this;
    }

    public Builder news(News val) {
      news = val;
      return this;
    }

    public NewsRead build() {
      return new NewsRead(this);
    }
  }
}
