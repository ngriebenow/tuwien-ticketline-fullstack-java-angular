package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Client {

  private static final String ID_SEQUENCE_NAME = "seq_client_id";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
  @SequenceGenerator(name = ID_SEQUENCE_NAME)
  private Long id;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  public Client() {
  }

  private Client(Builder builder) {
    setId(builder.id);
    setSurname(builder.surname);
    setName(builder.name);
    setEmail(builder.email);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Client client = (Client) obj;
    return Objects.equal(id, client.id)
        && Objects.equal(surname, client.surname)
        && Objects.equal(name, client.name)
        && Objects.equal(email, client.email);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, surname, name, email);
  }

  @Override
  public String toString() {
    return "Client{"
        + "id="
        + id
        + ", surname='"
        + surname
        + '\''
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }

  public static final class Builder {

    private Long id;
    private String surname;
    private String name;
    private String email;

    public Builder() {
    }

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder surname(String val) {
      surname = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder email(String val) {
      email = val;
      return this;
    }

    public Client build() {
      return new Client(this);
    }
  }
}
