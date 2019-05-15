package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

  private Client Client_1 =
      new Client.Builder()
          .name("Harald")
          .surname("Tester")
          .email("haral.tester@gmail.com")
          .build();

  @Autowired
  ClientRepository clientRepository;

  @Before
  public void setUp() {
    clientRepository.save(Client_1);
  }

  @Test
  public void givenClientSaved_whenFindClientById_thenReturnClient() {
    Client client = clientRepository.findById(Client_1.getId()).get();
    assertThat(client).isEqualTo(Client_1);
  }

  @Test(expected = NotFoundException.class)
  public void givenClientSaved_whenFindUnknownClientById_thenThrowNotFoundException() {
    clientRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
