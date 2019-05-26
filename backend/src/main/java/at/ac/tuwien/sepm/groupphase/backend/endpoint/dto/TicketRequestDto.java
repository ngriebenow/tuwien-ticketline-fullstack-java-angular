package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(
    value = "TicketRequest",
    description = "Specifies how many tickets to request for a defined unit")
public class TicketRequestDto {

  @NotNull(message = "definedUnitId has to be set")
  @ApiModelProperty(value = "The defined unit to get tickets for", required = true)
  private Long definedUnitId;

  @NotNull(message = "amount has to be set")
  @Min(value = 1, message = "amount has to be at least {value}")
  @ApiModelProperty(value = "The amount of tickets to request", required = true)
  private int amount;

  public TicketRequestDto() {
  }

  private TicketRequestDto(Builder builder) {
    setDefinedUnitId(builder.definedUnitId);
    setAmount(builder.amount);
  }

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

  public static final class Builder {

    private Long definedUnitId;
    private int amount;

    public Builder() {
    }

    public Builder definedUnitId(Long val) {
      definedUnitId = val;
      return this;
    }

    public Builder amount(int val) {
      amount = val;
      return this;
    }

    public TicketRequestDto build() {
      return new TicketRequestDto(this);
    }
  }
}
