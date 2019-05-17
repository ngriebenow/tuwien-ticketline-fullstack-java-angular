package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
    value = "TicketRequest",
    description = "Specifies how many tickets to request for a defined unit")
public class TicketRequestDto {

  @ApiModelProperty(value = "The defined unit to get tickets for", required = true)
  private Long definedUnitId;

  @ApiModelProperty(value = "The amount of tickets to request", required = true)
  private int amount;

  public Long getDefinedUnitId() {
    return definedUnitId;
  }

  public void setDefinedUnitId(Long definedUnitId) {
    this.definedUnitId = definedUnitId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
