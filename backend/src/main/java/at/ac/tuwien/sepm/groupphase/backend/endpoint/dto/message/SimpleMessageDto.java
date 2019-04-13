package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Objects;

@ApiModel(value = "DetailedMessageDto", description = "A simple DTO for message entries via rest")
public class SimpleMessageDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(
      required = true,
      readOnly = true,
      name = "The date and time when the message was published")
  private LocalDateTime publishedAt;

  @ApiModelProperty(required = true, readOnly = true, name = "The title of the message")
  private String title;

  @ApiModelProperty(required = true, readOnly = true, name = "The summary of the message")
  private String summary;

  public static MessageDtoBuilder builder() {
    return new MessageDtoBuilder();
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

  @Override
  public String toString() {
    return "SimpleMessageDto{"
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
    SimpleMessageDto that = (SimpleMessageDto) obj;
    return Objects.equals(id, that.id)
        && Objects.equals(publishedAt, that.publishedAt)
        && Objects.equals(title, that.title)
        && Objects.equals(summary, that.summary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publishedAt, title, summary);
  }

  public static final class MessageDtoBuilder {

    private Long id;
    private LocalDateTime publishedAt;
    private String title;
    private String summary;

    public MessageDtoBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public MessageDtoBuilder publishedAt(LocalDateTime publishedAt) {
      this.publishedAt = publishedAt;
      return this;
    }

    public MessageDtoBuilder title(String title) {
      this.title = title;
      return this;
    }

    public MessageDtoBuilder summary(String summary) {
      this.summary = summary;
      return this;
    }

    /** TODO: Add JavaDoc. */
    public SimpleMessageDto build() {
      SimpleMessageDto messageDto = new SimpleMessageDto();
      messageDto.setId(id);
      messageDto.setPublishedAt(publishedAt);
      messageDto.setTitle(title);
      messageDto.setSummary(summary);
      return messageDto;
    }
  }
}
