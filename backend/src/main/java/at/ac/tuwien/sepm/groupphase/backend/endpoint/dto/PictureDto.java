package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class PictureDto {

  private long id;

  private byte[] data;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
