package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import java.time.Duration;
import java.time.LocalDateTime;

public class EventFilterDto {

  private String name;

  private EventCategory eventCategory;

  private String content;

  private Duration duration;

  private ArtistDto artist;

  private Integer priceInCents;

  private HallDto hall;

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
