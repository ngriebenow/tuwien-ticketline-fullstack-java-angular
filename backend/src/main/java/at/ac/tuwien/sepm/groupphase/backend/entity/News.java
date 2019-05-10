package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.LocalDateTime;

public class News {

  private Long id;

  private LocalDateTime publishedAt;

  private String title;

  private String summary;

  private String text;

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
}