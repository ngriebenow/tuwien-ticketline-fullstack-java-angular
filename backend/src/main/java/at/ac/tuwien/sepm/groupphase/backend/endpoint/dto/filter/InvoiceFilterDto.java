package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

import java.util.Optional;

public class InvoiceFilterDto {

  private Optional<String> reservationCode;
  private Optional<Boolean> isPaid;
  private Optional<Boolean> isCancelled;
  private Optional<String> clientName;
  private Optional<String> clientEmail;
  private Optional<String> customerEmail;

  private InvoiceFilterDto(Builder builder) {
    setReservationCode(builder.reservationCode);
    setIsPaid(builder.isPaid);
    setIsCancelled(builder.isCancelled);
    setClientName(builder.clientName);
    setClientEmail(builder.clientEmail);
    setCustomerEmail(builder.customerEmail);
  }

  public Optional<String> getReservationCode() {
    return reservationCode;
  }

  public void setReservationCode(String reservationCode) {
    this.reservationCode = Optional.ofNullable(reservationCode);
  }

  public Optional<Boolean> getIsPaid() {
    return isPaid;
  }

  public void setIsPaid(Boolean isPaid) {
    this.isPaid = Optional.ofNullable(isPaid);
  }

  public Optional<Boolean> getIsCancelled() {
    return isCancelled;
  }

  public void setIsCancelled(Boolean isCancelled) {
    this.isCancelled = Optional.ofNullable(isCancelled);
  }

  public Optional<String> getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = Optional.ofNullable(clientName);
  }

  public Optional<String> getClientEmail() {
    return clientEmail;
  }

  public void setClientEmail(String clientEmail) {
    this.clientEmail = Optional.ofNullable(clientEmail);
  }

  public Optional<String> getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = Optional.ofNullable(customerEmail);
  }


  public static final class Builder {

    private String reservationCode;
    private Boolean isPaid;
    private Boolean isCancelled;
    private String clientName;
    private String clientEmail;
    private String customerEmail;

    public Builder() {
    }

    public Builder reservationCode(String val) {
      reservationCode = val;
      return this;
    }

    public Builder isPaid(Boolean val) {
      isPaid = val;
      return this;
    }

    public Builder isCancelled(Boolean val) {
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
