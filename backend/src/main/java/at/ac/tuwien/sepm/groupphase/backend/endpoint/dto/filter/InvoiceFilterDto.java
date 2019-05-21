package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

public class InvoiceFilterDto {

  private String reservationCode;
  private boolean isPaid;
  private boolean isCancelled;
  private String clientName;
  private String clientEmail;
  private String customerEmail;

  private InvoiceFilterDto(Builder builder) {
    setReservationCode(builder.reservationCode);
    setPaid(builder.isPaid);
    setCancelled(builder.isCancelled);
    setClientName(builder.clientName);
    setClientEmail(builder.clientEmail);
    setCustomerEmail(builder.customerEmail);
  }

  public String getReservationCode() {
    return reservationCode;
  }

  public void setReservationCode(String reservationCode) {
    this.reservationCode = reservationCode;
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

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getClientEmail() {
    return clientEmail;
  }

  public void setClientEmail(String clientEmail) {
    this.clientEmail = clientEmail;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public static final class Builder {

    private String reservationCode;
    private boolean isPaid;
    private boolean isCancelled;
    private String clientName;
    private String clientEmail;
    private String customerEmail;

    public Builder() {
    }

    public Builder reservationCode(String val) {
      reservationCode = val;
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

    public Builder clientName(String val) {
      clientName = val;
      return this;
    }

    public Builder clientEmail(String val) {
      clientEmail = val;
      return this;
    }

    public Builder customerEmail(String val) {
      customerEmail = val;
      return this;
    }

    public InvoiceFilterDto build() {
      return new InvoiceFilterDto(this);
    }
  }
}
