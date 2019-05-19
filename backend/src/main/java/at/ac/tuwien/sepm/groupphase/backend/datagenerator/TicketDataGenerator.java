package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("generateData")
public class TicketDataGenerator implements DataGenerator<Ticket> {

  private Set<Class<?>> dependencies =
      new HashSet<>(Arrays.asList(Performance.class, Invoice.class, DefinedUnit.class));
  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final Random RANDOM = new Random();

  private static final int MAX_TICKET_COUNT_PER_INVOICE = 10;
  private static final int MIN_TICKET_COUNT_PER_INVOICE = 1;
  private static final int SALT_LENGTH = 8;

  private TicketRepository ticketRepository;
  private DefinedUnitRepository definedUnitRepository;
  private PerformanceRepository performanceRepository;
  private InvoiceRepository invoiceRepository;

  /** Create a new TicketDataGenerator. */
  @Autowired
  public TicketDataGenerator(
      TicketRepository ticketRepository,
      DefinedUnitRepository definedUnitRepository,
      PerformanceRepository performanceRepository,
      InvoiceRepository invoiceRepository) {
    this.ticketRepository = ticketRepository;
    this.definedUnitRepository = definedUnitRepository;
    this.performanceRepository = performanceRepository;
    this.invoiceRepository = invoiceRepository;
  }

  @Transactional
  @Override
  public void execute() {

    byte[] salt = new byte[SALT_LENGTH];
    List<Performance> performances = performanceRepository.findAll();

    for (Invoice invoice : invoiceRepository.findAll()) {
      Performance performance = performances.get(FAKER.random().nextInt(performances.size()));
      List<DefinedUnit> definedUnits =
          definedUnitRepository.findAllByPerformanceAndCapacityFreeIsGreaterThan(performance, 0);
      int ticketCount =
          FAKER.random().nextInt(MIN_TICKET_COUNT_PER_INVOICE, MAX_TICKET_COUNT_PER_INVOICE);

      int definedUnitIdx = FAKER.random().nextInt(definedUnits.size());

      for (int i = 0; i < ticketCount; i++) {
        RANDOM.nextBytes(salt);
        DefinedUnit definedUnit = definedUnits.get((definedUnitIdx + i) % definedUnits.size());
        invoice.addTicket(
            new Ticket.Builder()
                .salt(Base64.getEncoder().encodeToString(salt))
                .isCancelled(invoice.isCancelled())
                .definedUnit(definedUnit)
                .build());

        definedUnit.setCapacityFree(definedUnit.getCapacityFree() - 1);
      }
      invoiceRepository.save(invoice);
    }
  }

  @Override
  public Class<Ticket> getGeneratedType() {
    return Ticket.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
