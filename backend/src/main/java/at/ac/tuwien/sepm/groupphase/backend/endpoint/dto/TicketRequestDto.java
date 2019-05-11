package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.List;

public class TicketRequestDto {

  private List<Long> definedUnitIds;

  private Long clientId;

  private boolean reserveAndSell;

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

  public boolean isReserveAndSell() {
    return reserveAndSell;
  }

  public void setReserveAndSell(boolean reserveAndSell) {
    this.reserveAndSell = reserveAndSell;
  }
}
