package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "EventCategoryDto", description = "A DTO for an event category via rest")
public enum EventCategoryDto {
  CINEMA,
  CONCERT,
  OTHER
}
