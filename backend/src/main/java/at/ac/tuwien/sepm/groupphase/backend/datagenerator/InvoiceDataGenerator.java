package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.util.InvoiceNumberSequenceGenerator;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class InvoiceDataGenerator implements DataGenerator<Invoice> {

  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(Client.class));
  private static final Faker FAKER = new Faker(new Locale("de-at"));

  private static final int MAX_INVOICE_COUNT_PER_CLIENT = 13;

  private ClientRepository clientRepository;
  private InvoiceRepository invoiceRepository;
  private InvoiceNumberSequenceGenerator invoiceNumberSequenceGenerator;

  /** Construct a new InvoceGenerator. */
  @Autowired
  public InvoiceDataGenerator(
      ClientRepository clientRepository,
      InvoiceRepository invoiceRepository,
      InvoiceNumberSequenceGenerator invoiceNumberSequenceGenerator) {
    this.clientRepository = clientRepository;
    this.invoiceRepository = invoiceRepository;
    this.invoiceNumberSequenceGenerator = invoiceNumberSequenceGenerator;
  }

  @Override
  public void execute() {
    List<Invoice> generatedInvoices = new ArrayList<>(MAX_INVOICE_COUNT_PER_CLIENT);

    for (Client client : clientRepository.findAll()) {
      generatedInvoices.clear();
      for (int i = 0; i < FAKER.random().nextInt(MAX_INVOICE_COUNT_PER_CLIENT); i++) {
        boolean isPaid = FAKER.random().nextBoolean();
        Long number = isPaid ? invoiceNumberSequenceGenerator.getNext() : null;
        boolean isCancelled = isPaid ? FAKER.random().nextBoolean() : false;
        // TODO: maybe add the paidAt property
        generatedInvoices.add(
            new Invoice.Builder()
                .reservationCode(FAKER.letterify("??????"))
                .isPaid(isPaid)
                .number(number)
                .isCancelled(isCancelled)
                .client(client)
                .build());
      }
      invoiceRepository.saveAll(generatedInvoices);
    }
  }

  @Override
  public Class<Invoice> getGeneratedType() {
    return Invoice.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
