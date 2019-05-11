package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PriceCategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "PerformanceFilterDto", description = "A DTO for filtering events")
public class EventFilterDto {


  @ApiModelProperty(required = false, name = "The name which the event should contain")
  private String name;

  @ApiModelProperty(required = false, name = "The category which the event should be")
  private EventCategory eventCategory;

  @ApiModelProperty(required = false, name = "The artist which should feature the event")
  private ArtistDto artist;

  @ApiModelProperty(required = false, name = "The price which one ticket should cost")
  private Integer priceInCents;

  @ApiModelProperty(required = false, name = "The hall in which the event should take place")
  private HallDto hall;

  @ApiModelProperty(required = false, name = "The content description which the event should have")
  private String content;

  @ApiModelProperty(required = false, name = "The duration how long the event should take")
  private Duration duration;

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

  public ArtistDto getArtist() {
    return artist;
  }

  public void setArtist(ArtistDto artist) {
    this.artist = artist;
  }

  public Integer getPriceInCents() {
    return priceInCents;
  }

  public void setPriceInCents(Integer priceInCents) {
    this.priceInCents = priceInCents;
  }

  public HallDto getHall() {
    return hall;
  }

  public void setHall(HallDto hall) {
    this.hall = hall;
  }
}
