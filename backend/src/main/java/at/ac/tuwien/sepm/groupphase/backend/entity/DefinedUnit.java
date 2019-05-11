package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.List;

public class DefinedUnit {

  private Long id;

  private int capacityFree;

  private Unit unit;

  private List<Ticket> ticket;

  private PriceCategory priceCategory;

  private Performance performance;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getCapacityFree() {
    return capacityFree;
  }

  public void setCapacityFree(int capacityFree) {
    this.capacityFree = capacityFree;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public List<Ticket> getTicket() {
    return ticket;
  }

  public void setTicket(List<Ticket> ticket) {
    this.ticket = ticket;
  }

  public PriceCategory getPriceCategory() {
    return priceCategory;
  }

  public void setPriceCategory(PriceCategory priceCategory) {
    this.priceCategory = priceCategory;
  }

  public Performance getPerformance() {
    return performance;
  }

  public void setPerformance(Performance performance) {
    this.performance = performance;
  }
}
