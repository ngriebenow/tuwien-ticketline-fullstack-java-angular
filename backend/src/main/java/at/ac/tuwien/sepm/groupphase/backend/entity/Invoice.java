package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Invoice {

  private static final String ID_SEQUENCE_NAME = "seq_invoice_id";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
  @SequenceGenerator(name = ID_SEQUENCE_NAME)
  private Long id;

  @Column(nullable = false)
  private boolean isPaid;

  @Column(nullable = false)
  private boolean isCancelled;

  // TODO: This might be better as uuid
  // TODO: What to do when this invoice is not a reservation and it gets paid right away?
  @Column(nullable = false)
  private String reservationCode;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Customer customer;

  /// TODO: Implement as soon as michis user persistance part is done
  // private User soldBy;

  @OneToMany(mappedBy = "invoice")
  private List<Ticket> tickets;

  public Invoice() {}

  private Invoice(Builder builder) {
    setId(builder.id);
    setPaid(builder.isPaid);
    setCancelled(builder.isCancelled);
    setReservationCode(builder.reservationCode);
    setCustomer(builder.customer);
    setTickets(builder.tickets);
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Invoice invoice = (Invoice) obj;
    return isPaid == invoice.isPaid
        && isCancelled == invoice.isCancelled
        && Objects.equal(id, invoice.id)
        && Objects.equal(reservationCode, invoice.reservationCode)
        && Objects.equal(customer, invoice.customer)
        && Objects.equal(tickets, invoice.tickets);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, isPaid, isCancelled, reservationCode, customer, tickets);
  }

  @Override
  public String toString() {
    return "Invoice{"
        + "id="
        + id
        + ", isPaid="
        + isPaid
        + ", isCancelled="
        + isCancelled
        + ", reservationCode='"
        + reservationCode
        + '\''
        + ", customer="
        + customer
        + ", tickets="
        + tickets
        + '}';
  }

  public static final class Builder {

    private Long id;
    private boolean isPaid;
    private boolean isCancelled;
    private String reservationCode;
    private Customer customer;
    private List<Ticket> tickets;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder isPaid(boolean val) {
      isPaid = val;
      return this;
    }

    public Builder isCancelled(boolean val) {
      isCancelled = val;
      return this;
    }

    public Builder reservationCode(String val) {
      reservationCode = val;
      return this;
    }

    public Builder customer(Customer val) {
      customer = val;
      return this;
    }

    public Builder tickets(List<Ticket> val) {
      tickets = val;
      return this;
    }

    public Invoice build() {
      return new Invoice(this);
    }
  }
}
