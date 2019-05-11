package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class CustomerDataGenerator implements DataGenerator<Customer> {

  private static Set<Class<?>> dependencies = new HashSet<>();
  private static final Faker FAKER = new Faker(new Locale("de-at"));

  private static final int MAX_CUSTOMER_COUNT = 103;

  private CustomerRepository customerRepository;

  @Autowired
  public CustomerDataGenerator(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public void execute() {
    List<Customer> generatedCustomers = new ArrayList<>(MAX_CUSTOMER_COUNT);
    for (int i = 0; i < MAX_CUSTOMER_COUNT; i++) {
      String name = FAKER.name().firstName();
      String surName = FAKER.name().lastName();
      String email =
          String.format(
              "%s@%s.%s",
              name.toLowerCase(), surName.toLowerCase(), FAKER.internet().domainSuffix());
      generatedCustomers.add(
          new Customer.Builder().name(name).surname(surName).email(email).build());
    }
    customerRepository.saveAll(generatedCustomers);
  }

  @Override
  public Class<Customer> getGeneratedType() {
    return Customer.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
