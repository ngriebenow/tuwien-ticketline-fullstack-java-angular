package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

@ApiModel(value = "PerformanceFilterDto", description = "A DTO for filtering performances")
public class PerformanceFilterDto {

  @ApiModelProperty(required = false, name = "The name which the performanc should contain")
  private LocalDateTime startAt;

  @ApiModelProperty(required = true, name = "The id of the event to which the performance should belong")
  private Long eventId;

  @ApiModelProperty(required = false, name = "The name of the performance")
  private String name;
}
