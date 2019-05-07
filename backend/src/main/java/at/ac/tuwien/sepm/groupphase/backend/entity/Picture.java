package at.ac.tuwien.sepm.groupphase.backend.entity;

public class Picture {

  private long id;

  private byte[] data;

  private News news;

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

  public News getNews() {
    return news;
  }

  public void setNews(News news) {
    this.news = news;
  }
}
