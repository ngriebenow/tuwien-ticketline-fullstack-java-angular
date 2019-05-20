package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Duration;
import java.util.List;

@ApiModel(value = "EventDto", description = "A DTO for an event via rest")
public class EventDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The name of the event")
  private String name;

  @ApiModelProperty(required = true, name = "The category of the event")
  private EventCategory category;

  @ApiModelProperty(name = "The content description of the event")
  private String content;

  @ApiModelProperty(required = true, name = "The duration of the event")
  private Duration duration;

  @ApiModelProperty(required = true, name = "The artists who feature the event")
  private List<ArtistDto> artists;

  @ApiModelProperty(required = true, name = "The hall in which the event takes place")
  private HallDto hall;

  @ApiModelProperty(required = true, name = "The price categories of the event")
  private List<PriceCategoryDto> priceCategories;

  private List<PerformanceDto> performances;

  public List<PerformanceDto> getPerformances() {
    return performances;
  }

  public void setPerformances(
      List<PerformanceDto> performances) {
    this.performances = performances;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventCategory getCategory() {
    return category;
  }

  public void setCategory(EventCategory category) {
    this.category = category;
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

  public List<ArtistDto> getArtists() {
    return artists;
  }

  public void setArtists(List<ArtistDto> artists) {
    this.artists = artists;
  }

  public HallDto getHall() {
    return hall;
  }

  public void setHall(HallDto hall) {
    this.hall = hall;
  }

  public List<PriceCategoryDto> getPriceCategories() {
    return priceCategories;
  }

  public void setPriceCategories(List<PriceCategoryDto> priceCategories) {
    this.priceCategories = priceCategories;
  }

  /** Build the event dto. */
  public EventDto build() {
    EventDto eventDto = new EventDto();
    eventDto.setId(id);
    eventDto.setContent(content);
    eventDto.setName(name);
    eventDto.setCategory(category);
    eventDto.setDuration(duration);
    return eventDto;
  }
}
