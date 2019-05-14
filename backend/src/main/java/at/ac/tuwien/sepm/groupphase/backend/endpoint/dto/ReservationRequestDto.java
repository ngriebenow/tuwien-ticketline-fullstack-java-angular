package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.List;

public class ReservationRequestDto {

  private List<Long> definedUnitIds;

  private Long clientId;

  public List<Long> getDefinedUnitIds() {
    return definedUnitIds;
  }

  public void setDefinedUnitIds(List<Long> definedUnitIds) {
    this.definedUnitIds = definedUnitIds;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }
}
