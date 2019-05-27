package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InvoiceRepositoryTest {

  @Autowired
  InvoiceRepository invoiceRepository;
  private Invoice INVOICE_1 =
      new Invoice.Builder()
          .isCancelled(false)
          .isPaid(false)
          .reservationCode("RANDOMSTRING")
          .build();

  @Before
  public void setUp() {
    invoiceRepository.save(INVOICE_1);
  }

  @Test
  public void givenInvoiceSaved_whenFindInvoiceById_thenReturnInvoice() {
    Invoice invoice = invoiceRepository.findById(INVOICE_1.getId()).get();
    assertThat(invoice).isEqualTo(INVOICE_1);
  }

  @Test(expected = NotFoundException.class)
  public void givenInvoiceSaved_whenFindUnknownInvoiceById_thenThrowNotFoundException() {
    invoiceRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
