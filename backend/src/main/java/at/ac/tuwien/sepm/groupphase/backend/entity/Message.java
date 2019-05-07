package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

@Entity
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_message_id")
  @SequenceGenerator(name = "seq_message_id", sequenceName = "seq_message_id")
  private Long id;

  @Column(nullable = false, name = "published_at")
  private LocalDateTime publishedAt;

  @Column(nullable = false)
  @Size(max = 100)
  private String title;

  @Column(nullable = false, length = 10_000)
  private String text;

  public static MessageBuilder builder() {
    return new MessageBuilder();
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
    return "Message{"
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
    Message message = (Message) obj;
    return Objects.equals(id, message.id)
        && Objects.equals(publishedAt, message.publishedAt)
        && Objects.equals(title, message.title)
        && Objects.equals(text, message.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publishedAt, title, text);
  }

  public static final class MessageBuilder {
    private Long id;
    private LocalDateTime publishedAt;
    private String title;
    private String text;

    private MessageBuilder() {}

    public MessageBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public MessageBuilder publishedAt(LocalDateTime publishedAt) {
      this.publishedAt = publishedAt;
      return this;
    }

    public MessageBuilder title(String title) {
      this.title = title;
      return this;
    }

    public MessageBuilder text(String text) {
      this.text = text;
      return this;
    }

    /** TODO: Add JavaDoc. */
    public Message build() {
      Message message = new Message();
      message.setId(id);
      message.setPublishedAt(publishedAt);
      message.setTitle(title);
      message.setText(text);
      return message;
    }
  }
}
