package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.List;

public class InvoiceDto {

  private Long id;

  private List<TicketDto> tickets;

  private String sellerUsername;

  private boolean isCancelled;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<TicketDto> getTickets() {
    return tickets;
  }

  public void setTickets(List<TicketDto> tickets) {
    this.tickets = tickets;
  }

  public String getSellerUsername() {
    return sellerUsername;
  }

  public void setSellerUsername(String sellerUsername) {
    this.sellerUsername = sellerUsername;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  public void setCancelled(boolean cancelled) {
    isCancelled = cancelled;
  }
}