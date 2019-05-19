package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

  @Autowired CustomerRepository customerRepository;
  private Customer CUSTOMER_1 =
      new Customer.Builder()
          .name("Harald")
          .surname("Tester")
          .email("haral.tester@gmail.com")
          .build();

  @Before
  public void setUp() {
    customerRepository.save(CUSTOMER_1);
  }

  @Test
  public void givenCustomerSaved_whenFindCustomerById_thenReturnCustomer() {
    Customer customer = customerRepository.findById(CUSTOMER_1.getId()).get();
    assertThat(customer).isEqualTo(CUSTOMER_1);
  }

  @Test(expected = NotFoundException.class)
  public void givenCustomerSaved_whenFindUnknownCustomerById_thenThrowNotFoundException() {
    customerRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
