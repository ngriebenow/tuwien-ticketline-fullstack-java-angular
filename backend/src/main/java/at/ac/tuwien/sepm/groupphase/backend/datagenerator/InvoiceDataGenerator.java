package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.util.InvoiceNumberSequenceGenerator;
import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("generateData")
public class InvoiceDataGenerator implements DataGenerator<Invoice> {

  private final Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(Client.class));
  private static final Faker FAKER = new Faker(new Locale("de-at"));

  private static final int MAX_INVOICE_COUNT_PER_CLIENT = 13;

  private ClientRepository clientRepository;
  private InvoiceRepository invoiceRepository;
  private InvoiceNumberSequenceGenerator invoiceNumberSequenceGenerator;

  /**
   * Construct a new InvoceGenerator.
   */
  @Autowired
  public InvoiceDataGenerator(
      ClientRepository clientRepository,
      InvoiceRepository invoiceRepository,
      InvoiceNumberSequenceGenerator invoiceNumberSequenceGenerator) {
    this.clientRepository = clientRepository;
    this.invoiceRepository = invoiceRepository;
    this.invoiceNumberSequenceGenerator = invoiceNumberSequenceGenerator;
  }

  @Transactional
  @Override
  public void execute() {
    List<Invoice> generatedInvoices = new ArrayList<>(MAX_INVOICE_COUNT_PER_CLIENT);

    for (Client client : clientRepository.findAll()) {
      generatedInvoices.clear();
      for (int i = 0; i < FAKER.random().nextInt(MAX_INVOICE_COUNT_PER_CLIENT); i++) {
        boolean isPaid = FAKER.random().nextBoolean();
        Invoice.Builder invoiceBuilder =
            new Invoice.Builder()
                .reservationCode(FAKER.letterify("??????"))
                .isPaid(isPaid)
                .client(client)
                .isCancelled(false);
        if (isPaid) {
          Date today = localDateToDate(LocalDate.now());
          LocalDate paidAt = dateToLocalDate(FAKER.date().past(14, TimeUnit.DAYS, today));
          invoiceBuilder
              .number(invoiceNumberSequenceGenerator.getNext())
              .isCancelled(FAKER.random().nextBoolean())
              .paidAt(paidAt);
        }
        generatedInvoices.add(invoiceBuilder.build());
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

  private Date localDateToDate(LocalDate dateToConvert) {
    return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  private LocalDate dateToLocalDate(Date dateToConvert) {
    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
