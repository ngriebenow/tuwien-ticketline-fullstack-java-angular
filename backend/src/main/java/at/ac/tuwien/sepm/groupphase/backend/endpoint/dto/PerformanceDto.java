package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

@ApiModel(value = "PerformanceDto", description = "A DTO for a performance via rest")
public class PerformanceDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The name of the performance")
  private LocalDateTime startAt;

  @ApiModelProperty(required = true, name = "The corresponding event of the performance")
  private EventDto event;

  @ApiModelProperty(required = true, name = "The name of the performance")
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getStartAt() {
    return startAt;
  }

  public void setStartAt(LocalDateTime startAt) {
    this.startAt = startAt;
  }

  public EventDto getEvent() {
    return event;
  }

  public void setEvent(EventDto event) {
    this.event = event;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Build the performance dto.
   */
  public PerformanceDto build() {
    PerformanceDto performanceDto = new PerformanceDto();
    performanceDto.setId(id);
    // performanceDto.setId(event);
    performanceDto.setName(name);
    performanceDto.setStartAt(startAt);
    return performanceDto;
  }
}
