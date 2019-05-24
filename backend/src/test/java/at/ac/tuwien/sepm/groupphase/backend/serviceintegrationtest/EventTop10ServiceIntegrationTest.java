package at.ac.tuwien.sepm.groupphase.backend.serviceintegrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.DefinedUnit;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Unit;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventSearchResultMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceSearchResultMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.DefinedUnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UnitRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import java.awt.Color;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "serviceintegration-test")
public class EventTop10ServiceIntegrationTest {


  @Autowired private ClientRepository clientRepository;
  @Autowired private LocationRepository locationRepository;
  @Autowired private HallRepository hallRepository;
  @Autowired private ArtistRepository artistRepository;
  @Autowired private EventRepository eventRepository;
  @Autowired private UnitRepository unitRepository;
  @Autowired private PerformanceRepository performanceRepository;
  @Autowired private PriceCategoryRepository priceCategoryRepository;
  @Autowired private DefinedUnitRepository definedUnitRepository;
  @Autowired private TicketRepository ticketRepository;
  @Autowired private InvoiceRepository invoiceRepository;

  @Autowired private EventService eventService;

  private Client clientOne;
  private Client clientTwo;
  private Location location;
  private Artist artist;
  private Hall hall;
  private Event event;
  private Unit unit1;
  private Unit unit2;
  private Performance performance1;
  private Performance performance2;
  private PriceCategory priceCategory1;
  private PriceCategory priceCategory2;
  private DefinedUnit definedUnit1;
  private DefinedUnit definedUnit2;
  private DefinedUnit definedUnit3;
  private DefinedUnit definedUnit4;
  private Ticket ticket1;
  private Invoice invoice1;

  @Transactional
  @BeforeEach
  public void setUp() {
    clientOne =
        new Client.Builder().name("Klaus").surname("Klauser").email("klaus@klausur.at").build();
    clientOne = clientRepository.save(clientOne);

    clientTwo =
        new Client.Builder()
            .name("Rabarbara")
            .surname("Arabrabar")
            .email("rabarbara@arabrabar.at")
            .build();
    clientTwo = clientRepository.save(clientTwo);

    location =
        new Location.Builder()
            .name("Location 1")
            .place("Place 1")
            .postalCode("1110")
            .street("Street 1")
            .country("Austria")
            .build();
    location = locationRepository.save(location);

    hall =
        new Hall.Builder()
            .name("Hall 1")
            .location(location)
            .boundaryPoint(new Point(10, 20))
            .version(1)
            .build();
    hall = hallRepository.save(hall);

    artist = new Artist.Builder().name("Bob").surname("Dylan").build();
    artist = artistRepository.save(artist);

    event =
        new Event.Builder()
            .name("Freuden der Freizeit")
            .duration(Duration.ofMinutes(90L))
            .content("Alle wollen mal ausspannen und neues ausprobieren. Bist du dabei?")
            .category(EventCategory.CONCERT)
            .hall(hall)
            .artists(Collections.singletonList(artist))
            .build();
    event = eventRepository.save(event);

    unit1 =
        new Unit.Builder()
            .name("Reihe 1, Platz 8")
            .lowerBoundary(new Point(0, 7))
            .upperBoundary(new Point(0, 7))
            .capacity(1)
            .hall(hall)
            .build();
    unit1 = unitRepository.save(unit1);

    unit2 =
        new Unit.Builder()
            .name("Sektor Hinten")
            .lowerBoundary(new Point(0, 9))
            .upperBoundary(new Point(9, 19))
            .capacity(3)
            .hall(hall)
            .build();
    unit2 = unitRepository.save(unit2);

    performance1 =
        new Performance.Builder()
            .name("Sonntagskrunch")
            .event(event)
            .startAt(LocalDateTime.now().plusDays(3))
            .build();
    performance1 = performanceRepository.save(performance1);

    performance2 =
        new Performance.Builder()
            .name("Luftballonkochkurz")
            .event(event)
            .startAt(LocalDateTime.now().plusDays(4))
            .build();
    performance2 = performanceRepository.save(performance2);

    priceCategory1 =
        new PriceCategory.Builder()
            .name("Kategorie 1")
            .priceInCents(3200)
            .color(new Color(0, 0, 0))
            .event(event)
            .build();
    priceCategory1 = priceCategoryRepository.save(priceCategory1);

    priceCategory2 =
        new PriceCategory.Builder()
            .name("Kategorie 2")
            .priceInCents(1700)
            .color(new Color(0, 0, 0))
            .event(event)
            .build();
    priceCategory2 = priceCategoryRepository.save(priceCategory2);

    definedUnit1 =
        new DefinedUnit.Builder()
            .performance(performance1)
            .priceCategory(priceCategory1)
            .unit(unit1)
            .capacityFree(unit1.getCapacity())
            .build();
    definedUnit1 = definedUnitRepository.save(definedUnit1);

    definedUnit2 =
        new DefinedUnit.Builder()
            .performance(performance1)
            .priceCategory(priceCategory2)
            .unit(unit2)
            .capacityFree(unit2.getCapacity())
            .build();
    definedUnit2 = definedUnitRepository.save(definedUnit2);

    definedUnit3 =
        new DefinedUnit.Builder()
            .performance(performance2)
            .priceCategory(priceCategory1)
            .unit(unit1)
            .capacityFree(unit1.getCapacity())
            .build();
    definedUnit3 = definedUnitRepository.save(definedUnit3);

    definedUnit4 =
        new DefinedUnit.Builder()
            .performance(performance2)
            .priceCategory(priceCategory2)
            .unit(unit2)
            .capacityFree(unit2.getCapacity())
            .build();
    definedUnit4 =definedUnitRepository.save(definedUnit4);

    invoice1 =
        new Invoice.Builder()
        .reservationCode("NIC")
        .client(clientOne)
        .build();
    invoiceRepository.save(invoice1);

    definedUnit1 = definedUnitRepository.findAll().get(0);

    ticket1 =
        new Ticket.Builder()
            .definedUnit(definedUnit2)
            .isCancelled(false)
            .salt("pepper")
            .build();
    //ticket1 = ticketRepository.save(ticket1);

    invoice1.addTicket(ticket1);


    invoiceRepository.save(invoice1);


  }


  @AfterEach
  public void cleanUp() {
    invoice1.removeTicket(ticket1);
    invoiceRepository.save(invoice1);
    invoiceRepository.deleteAll();
    ticketRepository.deleteAll();
    invoiceRepository.deleteAll();
    priceCategoryRepository.deleteAll();
    performanceRepository.deleteAll();
    eventRepository.deleteAll();
    hallRepository.deleteAll();
    locationRepository.deleteAll();
    artistRepository.deleteAll();
  }


  @Test
  public void givenEvents_whenGetBestEvents_returnBestEvents() {
    eventService.getBestEvents(10);
  }

}
