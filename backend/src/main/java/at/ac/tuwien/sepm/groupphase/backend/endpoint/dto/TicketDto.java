package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

@ApiModel(value = "Ticket", description = "A ticket to a performance")
public class TicketDto {

  @ApiModelProperty("The unique identifier of a specific ticket")
  private Long id;

  @ApiModelProperty("The name of the seat or sector the ticket is for")
  private String title;

  @ApiModelProperty("The name of the event the ticket is for")
  private String eventName;

  @ApiModelProperty("The name of the performance the ticket is for")
  private String performanceName;

  @ApiModelProperty("The start date and time of the performance")
  private LocalDateTime startAt;

  @ApiModelProperty("The name of the tickets price category")
  private String priceCategoryName;

  @ApiModelProperty("The price of the ticket in european cents")
  private int priceInCents;

  @ApiModelProperty("The name of the location of the tickets performance")
  private String locationName;

  @ApiModelProperty("The name of the hall the performance is held in")
  private String hallName;

  @ApiModelProperty("The id of the defined unit reserved by this ticket")
  private Long definedUnitId;

  public TicketDto() {}

  private TicketDto(Builder builder) {
    setId(builder.id);
    setTitle(builder.title);
    setEventName(builder.eventName);
    setPerformanceName(builder.performanceName);
    setStartAt(builder.startAt);
    setPriceCategoryName(builder.priceCategoryName);
    setPriceInCents(builder.priceInCents);
    setLocationName(builder.locationName);
    setHallName(builder.hallName);
    setDefinedUnitId(builder.definedUnitId);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getPerformanceName() {
    return performanceName;
  }

  public void setPerformanceName(String performanceName) {
    this.performanceName = performanceName;
  }

  public LocalDateTime getStartAt() {
    return startAt;
  }

  public void setStartAt(LocalDateTime startAt) {
    this.startAt = startAt;
  }

  public String getPriceCategoryName() {
    return priceCategoryName;
  }

  public void setPriceCategoryName(String priceCategoryName) {
    this.priceCategoryName = priceCategoryName;
  }

  public int getPriceInCents() {
    return priceInCents;
  }

  public void setPriceInCents(int priceInCents) {
    this.priceInCents = priceInCents;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public String getHallName() {
    return hallName;
  }

  public void setHallName(String hallName) {
    this.hallName = hallName;
  }

  public Long getDefinedUnitId() {
    return definedUnitId;
  }

  public void setDefinedUnitId(Long definedUnitId) {
    this.definedUnitId = definedUnitId;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    TicketDto ticketDto = (TicketDto) obj;
    return priceInCents == ticketDto.priceInCents
        && Objects.equal(id, ticketDto.id)
        && Objects.equal(title, ticketDto.title)
        && Objects.equal(eventName, ticketDto.eventName)
        && Objects.equal(performanceName, ticketDto.performanceName)
        && Objects.equal(startAt, ticketDto.startAt)
        && Objects.equal(priceCategoryName, ticketDto.priceCategoryName)
        && Objects.equal(locationName, ticketDto.locationName)
        && Objects.equal(hallName, ticketDto.hallName)
        && Objects.equal(definedUnitId, ticketDto.definedUnitId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        id,
        title,
        eventName,
        performanceName,
        startAt,
        priceCategoryName,
        priceInCents,
        locationName,
        hallName,
        definedUnitId);
  }

  @Override
  public String toString() {
    return "TicketDto{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", eventName='"
        + eventName
        + '\''
        + ", performanceName='"
        + performanceName
        + '\''
        + ", startAt="
        + startAt
        + ", priceCategoryName='"
        + priceCategoryName
        + '\''
        + ", priceInCents="
        + priceInCents
        + ", locationName='"
        + locationName
        + '\''
        + ", hallName='"
        + hallName
        + '\''
        + ", definedUnitId='"
        + definedUnitId
        + '\''
        + '}';
  }

  public static final class Builder {

    private Long id;
    private String title;
    private String eventName;
    private String performanceName;
    private LocalDateTime startAt;
    private String priceCategoryName;
    private int priceInCents;
    private String locationName;
    private String hallName;
    private Long definedUnitId;

    public Builder() {
    }

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder title(String val) {
      title = val;
      return this;
    }

    public Builder eventName(String val) {
      eventName = val;
      return this;
    }

    public Builder performanceName(String val) {
      performanceName = val;
      return this;
    }

    public Builder startAt(LocalDateTime val) {
      startAt = val;
      return this;
    }

    public Builder priceCategoryName(String val) {
      priceCategoryName = val;
      return this;
    }

    public Builder priceInCents(int val) {
      priceInCents = val;
      return this;
    }

    public Builder locationName(String val) {
      locationName = val;
      return this;
    }

    public Builder hallName(String val) {
      hallName = val;
      return this;
    }

    public Builder definedUnitId(Long val) {
      definedUnitId = val;
      return this;
    }

    public TicketDto build() {
      return new TicketDto(this);
    }
  }
}
