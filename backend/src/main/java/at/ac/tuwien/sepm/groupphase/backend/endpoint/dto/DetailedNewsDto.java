package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ApiModel(value = "DetailedNewsDto", description = "A detailed DTO for news entries via rest")
public class DetailedNewsDto {

  @ApiModelProperty(name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(name = "The date and time when the news entry was published")
  private LocalDateTime publishedAt;

  @ApiModelProperty(required = true, name = "The title of the news entry")
  private String title;

  @ApiModelProperty(required = true, name = "The text content of the news entry")
  private String text;

  @ApiModelProperty(required = true, name = "The summary of the news entry")
  private String summary;

  @ApiModelProperty(required = false, name = "The list of picture ids for the news entry")
  private List<Long> pictureIds;

  public static NewsDtoBuilder builder() {
    return new NewsDtoBuilder();
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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public List<Long> getPictureIds() {
    return pictureIds;
  }

  public void setPictureIds(List<Long> pictureIds) {
    this.pictureIds = pictureIds;
  }

  @Override
  public String toString() {
    return "DetailedNewsDto{"
        + "id="
        + id
        + ", publishedAt="
        + publishedAt
        + ", title='"
        + title
        + '\''
        + ", text='"
        + text
        + '\''
        + ", summary='"
        + summary
        + ", pictureIds='"
        + pictureIds
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
    DetailedNewsDto that = (DetailedNewsDto) obj;
    return Objects.equals(id, that.id)
        && Objects.equals(title, that.title)
        && Objects.equals(text, that.text)
        && Objects.equals(summary, that.summary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publishedAt, title, text, summary, pictureIds);
  }

  public static final class NewsDtoBuilder {

    private Long id;
    private LocalDateTime publishedAt;
    private String title;
    private String text;
    private String summary;
    private List<Long> pictureIds;

    public NewsDtoBuilder id(Long id) {
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

    public NewsDtoBuilder text(String text) {
      this.text = text;
      return this;
    }

    public NewsDtoBuilder summary(String summary) {
      this.summary = summary;
      return this;
    }

    public NewsDtoBuilder pictureIds(List<Long> pictureIds) {
      this.pictureIds = pictureIds;
      return this;
    }

    /**
     * Build a DetailedNewsDto.
     *
     * @return the built DetailedNewsDto
     */
    public DetailedNewsDto build() {
      DetailedNewsDto newsDto = new DetailedNewsDto();
      newsDto.setId(id);
      newsDto.setPublishedAt(publishedAt);
      newsDto.setTitle(title);
      newsDto.setText(text);
      newsDto.setSummary(summary);
      newsDto.setPictureIds(pictureIds);
      return newsDto;
    }
  }
}
