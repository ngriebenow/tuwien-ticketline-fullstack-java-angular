package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.List;

public class Invoice {

  private long id;

  private boolean isPaid;

  private boolean isCancelled;

  private String reservationCode;

  private Customer customer;

  private User soldBy;

  private List<Ticket> tickets;

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  public void setCancelled(boolean cancelled) {
    isCancelled = cancelled;
  }

  public String getReservationCode() {
    return reservationCode;
  }

  public void setReservationCode(String reservationCode) {
    this.reservationCode = reservationCode;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public User getSoldBy() {
    return soldBy;
  }

  public void setSoldBy(User soldBy) {
    this.soldBy = soldBy;
  }
}
