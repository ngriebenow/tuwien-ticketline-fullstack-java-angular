package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Ticket {

  private static final String ID_SEQUENCE_NAME = "seq_ticket_id";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
  @SequenceGenerator(name = ID_SEQUENCE_NAME)
  private Long id;

  @Column(nullable = false)
  private String salt;

  @Column(nullable = false)
  private boolean isCancelled;

  @ManyToOne
  @JoinColumn(name = "invoice_id", nullable = false)
  private Invoice invoice;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(nullable = false)
  private DefinedUnit definedUnit;

  public Ticket() {}

  private Ticket(Builder builder) {
    setId(builder.id);
    setSalt(builder.salt);
    setCancelled(builder.isCancelled);
    setInvoice(builder.invoice);
    setDefinedUnit(builder.definedUnit);
  }

  public Invoice getInvoice() {
    return invoice;
  }

  public void setInvoice(Invoice invoice) {
    this.invoice = invoice;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  public void setCancelled(boolean cancelled) {
    isCancelled = cancelled;
  }

  public DefinedUnit getDefinedUnit() {
    return definedUnit;
  }

  public void setDefinedUnit(DefinedUnit definedUnit) {
    this.definedUnit = definedUnit;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Ticket ticket = (Ticket) obj;
    return isCancelled == ticket.isCancelled
        && Objects.equal(id, ticket.id)
        && Objects.equal(salt, ticket.salt)
        && Objects.equal(invoice, ticket.invoice)
        && Objects.equal(definedUnit, ticket.definedUnit);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, salt, isCancelled, invoice, definedUnit);
  }

  @Override
  public String toString() {
    return "Ticket{"
        + "id="
        + id
        + ", salt="
        + salt
        + ", isCancelled="
        + isCancelled
        + ", invoice="
        + invoice
        + ", definedUnit="
        + definedUnit
        + '}';
  }

  public static final class Builder {

    private Long id;
    private String salt;
    private boolean isCancelled;
    private Invoice invoice;
    private DefinedUnit definedUnit;

    public Builder() {
    }

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder salt(String val) {
      salt = val;
      return this;
    }

    public Builder isCancelled(boolean val) {
      isCancelled = val;
      return this;
    }

    public Builder invoice(Invoice val) {
      invoice = val;
      return this;
    }

    public Builder definedUnit(DefinedUnit val) {
      definedUnit = val;
      return this;
    }

    public Ticket build() {
      return new Ticket(this);
    }
  }
}
