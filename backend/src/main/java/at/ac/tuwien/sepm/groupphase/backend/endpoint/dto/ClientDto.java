package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.google.common.base.Objects;

public class ClientDto {

  private Long id;

  private String name;

  private String surname;

  private String email;

  public ClientDto() {}

  private ClientDto(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setSurname(builder.surname);
    setEmail(builder.email);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientDto clientDto = (ClientDto) o;
    return Objects.equal(id, clientDto.id)
        && Objects.equal(name, clientDto.name)
        && Objects.equal(surname, clientDto.surname)
        && Objects.equal(email, clientDto.email);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, name, surname, email);
  }

  @Override
  public String toString() {
    return "ClientDto{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", surname='"
        + surname
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }

  public static final class Builder {

    private Long id;
    private String name;
    private String surname;
    private String email;

    public Builder() {}

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder surname(String val) {
      surname = val;
      return this;
    }

    public Builder email(String val) {
      email = val;
      return this;
    }

    public ClientDto build() {
      return new ClientDto(this);
    }
  }
}
