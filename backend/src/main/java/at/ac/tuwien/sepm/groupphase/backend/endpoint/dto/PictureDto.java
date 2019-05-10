package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class PictureDto {

  private Long id;

  private byte[] data;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
