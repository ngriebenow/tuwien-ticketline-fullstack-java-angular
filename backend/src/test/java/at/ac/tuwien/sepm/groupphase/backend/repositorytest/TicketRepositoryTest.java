package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TicketRepositoryTest {

  private Ticket TICKET_1 =
      new Ticket.Builder().isCancelled(false).salt("RANDOMSTRING".getBytes()).build();

  @Autowired TicketRepository ticketRepository;

  @Before
  public void setUp() {
    ticketRepository.save(TICKET_1);
  }

  @Test
  public void givenTicketSaved_whenFindTicketById_thenReturnTicket() {
    Ticket ticket = ticketRepository.findById(TICKET_1.getId()).get();
    assertThat(ticket).isEqualTo(TICKET_1);
  }

  @Test(expected = NotFoundException.class)
  public void givenTicketSaved_whenFindUnknownTicketById_thenThrowNotFoundException()
      throws NotFoundException {
    ticketRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
