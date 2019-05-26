package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.io.Serializable;
import java.util.Objects;


public class UserNewsKey implements Serializable {

  private String user;
  private Long news;

  /**
   * Construct the user news key entry.
   */
  public UserNewsKey() {
  }

  private UserNewsKey(Builder builder) {
    setUser(builder.user);
    setNews(builder.news);
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Long getNews() {
    return news;
  }

  public void setNews(Long news) {
    this.news = news;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserNewsKey that = (UserNewsKey) o;
    return Objects.equals(user, that.user)
        && Objects.equals(news, that.news);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, news);
  }

  public static final class Builder {

    private String user;
    private Long news;

    public Builder() {
    }

    public Builder user(String val) {
      user = val;
      return this;
    }

    public Builder news(Long val) {
      news = val;
      return this;
    }

    public UserNewsKey build() {
      return new UserNewsKey(this);
    }
  }
}
