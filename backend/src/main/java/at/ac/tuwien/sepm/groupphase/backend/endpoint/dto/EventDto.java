package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import java.time.Duration;
import java.util.List;

public class EventDto {

  private long id;

  private String name;

  private EventCategory category;

  private String content;

  private Duration duration;

  private List<ArtistDto> artists;

  private HallDto hall;

  private List<PriceCategoryDto> priceCategories;

  public long getId() {
    return id;
  }

  public void setId(long id) {
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
}
