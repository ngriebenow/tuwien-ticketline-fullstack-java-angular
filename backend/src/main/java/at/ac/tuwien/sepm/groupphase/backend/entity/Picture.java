package at.ac.tuwien.sepm.groupphase.backend.entity;

public class Picture {

  private Long id;

  private byte[] data;

  private News news;

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

  public News getNews() {
    return news;
  }

  public void setNews(News news) {
    this.news = news;
  }
}
