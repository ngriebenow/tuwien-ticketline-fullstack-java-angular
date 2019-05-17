package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Specifies how many tickets to request for a defined unit")
public class TicketRequestDto {

  @ApiModelProperty("The defined unit to get tickets for")
  private Long definedUnitId;

  @ApiModelProperty("The amount of tickets to request")
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
