package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class TicketValidationDto {

  private Long id;

  private String hash;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }
}
