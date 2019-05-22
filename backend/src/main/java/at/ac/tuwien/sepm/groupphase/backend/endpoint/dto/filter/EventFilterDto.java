package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Duration;
import java.time.LocalDateTime;

@ApiModel(value = "PerformanceFilterDto", description = "A DTO for filtering events")
public class EventFilterDto {

  @ApiModelProperty(required = false, name = "The name which the event should contain")
  private String name;

  @ApiModelProperty(required = false, name = "The category which the event should be")
  private EventCategory eventCategory;

  @ApiModelProperty(required = false, name = "The artist which should feature the event")
  private String artistName;

  @ApiModelProperty(required = false, name = "The price which one ticket should cost")
  private Integer priceInCents;

  @ApiModelProperty(required = false, name = "The content description which the event should have")
  private String content;

  @ApiModelProperty(required = false, name = "The duration how long the event should take")
  private Duration duration;

  @ApiModelProperty(required = false, name = "The hall id")
  private Long hallId;

  @ApiModelProperty(required = false, name = "The hall name")
  private String hallName;

  @ApiModelProperty(required = false, name = "The location id")
  private Long locationId;

  @ApiModelProperty(required = false, name = "The location name")
  private String locationName;

  @ApiModelProperty(required = false, name = "The location place")
  private String locationPlace;

  @ApiModelProperty(required = false, name = "The location country")
  private String locationCountry;

  @ApiModelProperty(required = false, name = "The location street")
  private String locationStreet;

  @ApiModelProperty(required = false, name = "The location postal code")
  private String locationPostalCode;

  @ApiModelProperty(required = false, name = "The time at which the performance should take place")
  private LocalDateTime startAtTime;

  @ApiModelProperty(required = false, name = "The date at which the performance should take place")
  private LocalDateTime startAtDate;

  @ApiModelProperty(required = false, name = "The name of the performance")
  private String performanceName;

  public EventFilterDto() {}

  private EventFilterDto(Builder builder) {
    name = builder.name;
    eventCategory = builder.eventCategory;
    artistName = builder.artistName;
    priceInCents = builder.priceInCents;
    content = builder.content;
    duration = builder.duration;
    hallId = builder.hallId;
    hallName = builder.hallName;
    locationId = builder.locationId;
    locationName = builder.locationName;
    locationPlace = builder.locationPlace;
    locationCountry = builder.locationCountry;
    locationStreet = builder.locationStreet;
    locationPostalCode = builder.locationPostalCode;
  }

  public LocalDateTime getStartAtTime() {
    return startAtTime;
  }

  public void setStartAtTime(LocalDateTime startAtTime) {
    this.startAtTime = startAtTime;
  }

  public LocalDateTime getStartAtDate() {
    return startAtDate;
  }

  public void setStartAtDate(LocalDateTime startAtDate) {
    this.startAtDate = startAtDate;
  }

  public String getPerformanceName() {
    return performanceName;
  }

  public void setPerformanceName(String performanceName) {
    this.performanceName = performanceName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventCategory getEventCategory() {
    return eventCategory;
  }

  public void setEventCategory(EventCategory eventCategory) {
    this.eventCategory = eventCategory;
  }

  public String getArtistName() {
    return artistName;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }

  public Integer getPriceInCents() {
    return priceInCents;
  }

  public void setPriceInCents(Integer priceInCents) {
    this.priceInCents = priceInCents;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public Long getHallId() {
    return hallId;
  }

  public void setHallId(Long hallId) {
    this.hallId = hallId;
  }

  public String getHallName() {
    return hallName;
  }

  public void setHallName(String hallName) {
    this.hallName = hallName;
  }

  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public String getLocationPlace() {
    return locationPlace;
  }

  public void setLocationPlace(String locationPlace) {
    this.locationPlace = locationPlace;
  }

  public String getLocationCountry() {
    return locationCountry;
  }

  public void setLocationCountry(String locationCountry) {
    this.locationCountry = locationCountry;
  }

  public String getLocationStreet() {
    return locationStreet;
  }

  public void setLocationStreet(String locationStreet) {
    this.locationStreet = locationStreet;
  }

  public String getLocationPostalCode() {
    return locationPostalCode;
  }

  public void setLocationPostalCode(String locationPostalCode) {
    this.locationPostalCode = locationPostalCode;
  }

  public static final class Builder {

    private String name;
    private EventCategory eventCategory;
    private String artistName;
    private Integer priceInCents;
    private String content;
    private Duration duration;
    private Long hallId;
    private String hallName;
    private Long locationId;
    private String locationName;
    private String locationPlace;
    private String locationCountry;
    private String locationStreet;
    private String locationPostalCode;

    public Builder() {}

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder eventCategory(EventCategory val) {
      eventCategory = val;
      return this;
    }

    public Builder artistName(String val) {
      artistName = val;
      return this;
    }

    public Builder priceInCents(Integer val) {
      priceInCents = val;
      return this;
    }

    public Builder content(String val) {
      content = val;
      return this;
    }

    public Builder duration(Duration val) {
      duration = val;
      return this;
    }

    public Builder hallId(Long val) {
      hallId = val;
      return this;
    }

    public Builder hallName(String val) {
      hallName = val;
      return this;
    }

    public Builder locationId(Long val) {
      locationId = val;
      return this;
    }

    public Builder locationName(String val) {
      locationName = val;
      return this;
    }

    public Builder locationPlace(String val) {
      locationPlace = val;
      return this;
    }

    public Builder locationCountry(String val) {
      locationCountry = val;
      return this;
    }

    public Builder locationStreet(String val) {
      locationStreet = val;
      return this;
    }

    public Builder locationPostalCode(String val) {
      locationPostalCode = val;
      return this;
    }

    public EventFilterDto build() {
      return new EventFilterDto(this);
    }
  }
}
