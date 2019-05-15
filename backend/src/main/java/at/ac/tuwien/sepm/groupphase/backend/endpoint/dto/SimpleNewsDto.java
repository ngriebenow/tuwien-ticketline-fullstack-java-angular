package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Objects;

@ApiModel(value = "SimpleNewsDto", description = "A simple DTO for news entries via rest")
public class SimpleNewsDto {

  @ApiModelProperty(name = "The automatically generated database id")
  private long id;

  @ApiModelProperty(required = true, name = "The date and time when the news entry was published")
  private LocalDateTime publishedAt;

  @ApiModelProperty(required = true, name = "The title of the news entry")
  private String title;

  @ApiModelProperty(required = true, name = "The summary of the news entry")
  private String summary;

  public static NewsDtoBuilder builder() {
    return new NewsDtoBuilder();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  @Override
  public String toString() {
    return "SimpleNewsDto{"
        + "id="
        + id
        + ", publishedAt="
        + publishedAt
        + ", title='"
        + title
        + '\''
        + ", summary='"
        + summary
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SimpleNewsDto that = (SimpleNewsDto) obj;
    return Objects.equals(id, that.id)
        && Objects.equals(publishedAt, that.publishedAt)
        && Objects.equals(title, that.title)
        && Objects.equals(summary, that.summary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publishedAt, title, summary);
  }

  public static final class NewsDtoBuilder {

    private long id;
    private LocalDateTime publishedAt;
    private String title;
    private String summary;

    public NewsDtoBuilder id(long id) {
      this.id = id;
      return this;
    }

    public NewsDtoBuilder publishedAt(LocalDateTime publishedAt) {
      this.publishedAt = publishedAt;
      return this;
    }

    public NewsDtoBuilder title(String title) {
      this.title = title;
      return this;
    }

    public NewsDtoBuilder summary(String summary) {
      this.summary = summary;
      return this;
    }

    /**
     * Build a SimpleNewsDto.
     *
     * @return the built SimpleNewsDto
     */
    public SimpleNewsDto build() {
      SimpleNewsDto newsDto = new SimpleNewsDto();
      newsDto.setId(id);
      newsDto.setPublishedAt(publishedAt);
      newsDto.setTitle(title);
      newsDto.setSummary(summary);
      return newsDto;
    }
  }
}
