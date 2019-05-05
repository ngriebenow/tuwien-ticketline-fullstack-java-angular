package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class TicketDto {

  private String unitName;

  private String salt;

  private long invoiceId;

  private String clientName;

  private String clientSurname;

  private long id;

  private int priceInCents;

  private boolean isCancelled;

  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public long getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(long invoiceId) {
    this.invoiceId = invoiceId;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getClientSurname() {
    return clientSurname;
  }

  public void setClientSurname(String clientSurname) {
    this.clientSurname = clientSurname;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getPriceInCents() {
    return priceInCents;
  }

  public void setPriceInCents(int priceInCents) {
    this.priceInCents = priceInCents;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  public void setCancelled(boolean cancelled) {
    isCancelled = cancelled;
  }
}
