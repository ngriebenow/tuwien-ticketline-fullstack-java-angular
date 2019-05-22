package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter;

import java.util.Objects;

public class ClientFilterDto {

  private String name;

  private String surname;

  private String email;

  private Integer page;

  private Integer count;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientFilterDto that = (ClientFilterDto) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(surname, that.surname) &&
        Objects.equals(email, that.email) &&
        Objects.equals(page, that.page) &&
        Objects.equals(count, that.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname, email, page, count);
  }
}
