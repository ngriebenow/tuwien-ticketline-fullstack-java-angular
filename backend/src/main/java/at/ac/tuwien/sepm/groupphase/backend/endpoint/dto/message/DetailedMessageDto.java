package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Objects;

@ApiModel(value = "DetailedMessageDto", description = "A detailed DTO for message entries via rest")
public class DetailedMessageDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(readOnly = true, name = "The date and time when the message was published")
  private LocalDateTime publishedAt;

  @ApiModelProperty(required = true, name = "The title of the message")
  private String title;

  @ApiModelProperty(required = true, name = "The text content of the message")
  private String text;

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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "DetailedMessageDto{"
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
    DetailedMessageDto that = (DetailedMessageDto) obj;
    return Objects.equals(id, that.id)
        && Objects.equals(publishedAt, that.publishedAt)
        && Objects.equals(title, that.title)
        && Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publishedAt, title, text);
  }

  public static final class MessageDtoBuilder {

    private Long id;
    private LocalDateTime publishedAt;
    private String title;
    private String text;

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

    public MessageDtoBuilder text(String text) {
      this.text = text;
      return this;
    }

    /** TODO: Add JavaDoc. */
    public DetailedMessageDto build() {
      DetailedMessageDto messageDto = new DetailedMessageDto();
      messageDto.setId(id);
      messageDto.setPublishedAt(publishedAt);
      messageDto.setTitle(title);
      messageDto.setText(text);
      return messageDto;
    }
  }
}
