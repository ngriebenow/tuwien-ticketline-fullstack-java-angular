package at.ac.tuwien.sepm.groupphase.backend.serviceintegrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchResultDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.EventFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Point;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.event.EventSearchResultMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.performance.PerformanceSearchResultMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import java.awt.Color;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "serviceintegration-test")
public class EventServiceIntegrationTest {


  @Autowired
  private EventService eventService;

  @Autowired
  private PerformanceRepository performanceRepository;
  @Autowired
  private HallRepository hallRepository;
  @Autowired
  private LocationRepository locationRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private EventRepository eventRepository;
  @Autowired
  private PriceCategoryRepository priceCategoryRepository;

  @Autowired
  private EventMapper eventMapper;
  @Autowired
  private EventSearchResultMapper eventSearchResultMapper;
  @Autowired
  private PerformanceSearchResultMapper performanceSearchResultMapper;

  private Event E1;
  private Event E2;
  private Event E3;

  private static EventSearchResultDto E1_SR;
  private static EventSearchResultDto E2_SR;
  private static EventSearchResultDto E3_SR;

  private Artist A1;
  private Artist A2;
  private Artist A3;
  private Artist A4;

  private Hall H1;
  private Hall H2;
  private Hall H3;

  private Location L1;
  private Location L2;

  private Performance P1;
  private Performance P2;

  private Performance P3;
  private Performance P4;
  private Performance P5;

  private Performance P6;
  private Performance P7;
  private Performance P8;
  private Performance P9;

  private PriceCategory PC1;
  private PriceCategory PC2;

  private PriceCategory PC3;
  private PriceCategory PC4;

  private PriceCategory PC5;
  private PriceCategory PC6;


  @Before
  public void initialize() {

    E1 =
        new Event.Builder()
            .name("Abc")
            .category(EventCategory.CINEMA)
            .duration(Duration.ofHours(2))
            .content("Content1")
            .build();

    E2 =
        new Event.Builder()
            .name("Bcd")
            .category(EventCategory.CONCERT)
            .duration(Duration.ofHours(5))
            .content("Content2")
            .build();

    E3 =
        new Event.Builder()
            .name("Cde")
            .category(EventCategory.OTHER)
            .duration(Duration.ofHours(3))
            .content("Content3")
            .build();

    A1 = new Artist.Builder().surname("Artist Abcd").name("Artist Name W1").build();
    A2 = new Artist.Builder().surname("Artist Bcde").name("Artist Name W2").build();
    A3 = new Artist.Builder().surname("Artist Cdef").name("Artist Name W3").build();
    A4 = new Artist.Builder().surname("Artist Defg").name("Artist Name W4").build();

    A1 = artistRepository.save(A1);
    A2 = artistRepository.save(A2);
    A3 = artistRepository.save(A3);
    A4 = artistRepository.save(A4);

    E1.setArtists(List.of(A1));
    E2.setArtists(List.of(A1, A2, A3, A4));
    E3.setArtists(List.of(A2, A4));

    L1 =
        new Location.Builder()
            .name("Loc Abc")
            .street("Street 1")
            .postalCode("0000")
            .place("Frankenhausen")
            .country("Austria")
            .build();
    L1 = locationRepository.save(L1);

    L2 =
        new Location.Builder()
            .name("Loc Xyz")
            .street("Street 3")
            .postalCode("1111")
            .place("Steinfurt")
            .country("Germany")
            .build();
    L2 = locationRepository.save(L2);

    H1 =
        new Hall.Builder()
            .version(1)
            .name("Hall Goethe")
            .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
            .location(L1)
            .build();
    hallRepository.save(H1);

    H2 =
        new Hall.Builder()
            .version(1)
            .name("Hall Schiller")
            .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
            .location(L1)
            .build();
    hallRepository.save(H2);

    H3 =
        new Hall.Builder()
            .version(1)
            .name("Hall Wolkenstein")
            .boundaryPoint(new Point.Builder().coordinateX(0).coordinateY(0).build())
            .location(L1)
            .build();
    hallRepository.save(H3);

    E1.setHall(H1);
    E2.setHall(H2);
    E3.setHall(H3);

    eventRepository.save(E1);
    eventRepository.save(E2);
    eventRepository.save(E3);

    P1 =
        new Performance.Builder()
            .name("1A")
            .startAtLocalAndUtc(LocalDate.of(3000, 1, 15).atStartOfDay())
            .id(0L)
            .event(E1)
            .build();
    P1 = performanceRepository.save(P1);

    P2 =
        new Performance.Builder()
            .name("2A")
            .startAtLocalAndUtc(LocalDate.of(3000, 2, 15).atStartOfDay())
            .id(1L)
            .event(E1)
            .build();
    P2 = performanceRepository.save(P2);

    P3 =
        new Performance.Builder()
            .name("1B")
            .startAtLocalAndUtc(LocalDate.of(3000, 3, 15).atStartOfDay())
            .event(E2)
            .build();
    P3 = performanceRepository.save(P3);

    P4 =
        new Performance.Builder()
            .name("2B")
            .startAtLocalAndUtc(LocalDate.of(3000, 4, 15).atStartOfDay())
            .event(E2)
            .build();
    P4 = performanceRepository.save(P4);

    P5 =
        new Performance.Builder()
            .name("3B")
            .startAtLocalAndUtc(LocalDate.of(3000, 5, 15).atStartOfDay())
            .event(E2)
            .build();
    P5 = performanceRepository.save(P5);

    P6 =
        new Performance.Builder()
            .name("1C")
            .startAtLocalAndUtc(LocalDate.of(3000, 6, 15).atStartOfDay())
            .event(E3)
            .build();
    P6 = performanceRepository.save(P6);

    P7 =
        new Performance.Builder()
            .name("2C")
            .startAtLocalAndUtc(LocalDate.of(3000, 7, 15).atStartOfDay())
            .event(E3)
            .build();
    P7 = performanceRepository.save(P7);

    P8 =
        new Performance.Builder()
            .name("3C")
            .startAtLocalAndUtc(LocalDate.of(3000, 8, 15).atStartOfDay())
            .event(E3)
            .build();
    P8 = performanceRepository.save(P8);

    P9 =
        new Performance.Builder()
            .name("4C")
            .startAtLocalAndUtc(LocalDate.of(3000, 9, 15).atStartOfDay())
            .event(E3)
            .build();
    P9 = performanceRepository.save(P9);

    PC1 = new PriceCategory.Builder()
        .name("PC1")
        .color(Color.BLACK)
        .event(E1)
        .priceInCents(2000).build();
    PC1 = priceCategoryRepository.save(PC1);

    PC2 = new PriceCategory.Builder()
        .name("PC2")
        .color(Color.BLACK)
        .event(E2)
        .priceInCents(3000).build();
    PC2 = priceCategoryRepository.save(PC2);

    PC3 = new PriceCategory.Builder()
        .name("PC3")
        .color(Color.BLACK)
        .event(E2)
        .priceInCents(4000).build();
    PC3 = priceCategoryRepository.save(PC3);

    PC4 = new PriceCategory.Builder()
        .name("PC4")
        .color(Color.BLACK)
        .event(E3)
        .priceInCents(5000).build();
    PC4 = priceCategoryRepository.save(PC4);

    PC5 = new PriceCategory.Builder()
        .name("PC5")
        .color(Color.BLACK)
        .event(E3)
        .priceInCents(6000).build();
    PC5 = priceCategoryRepository.save(PC5);

    PC6 = new PriceCategory.Builder()
        .name("PC6")
        .color(Color.BLACK)
        .event(E3)
        .priceInCents(7000).build();
    PC6 = priceCategoryRepository.save(PC6);

    E1_SR = eventSearchResultMapper.eventToEventSearchResultDto(E1);
    E1_SR.setPriceRange("20 €");
    E2_SR = eventSearchResultMapper.eventToEventSearchResultDto(E2);
    E2_SR.setPriceRange("30 - 40 €");
    E3_SR = eventSearchResultMapper.eventToEventSearchResultDto(E3);
    E3_SR.setPriceRange("50 - 70 €");

  }

  @After
  public void cleanUp() {
    priceCategoryRepository.deleteAll();
    performanceRepository.deleteAll();
    eventRepository.deleteAll();
    hallRepository.deleteAll();
    locationRepository.deleteAll();
    artistRepository.deleteAll();
  }

  @Test
  public void givenEvent_whenFindByEvent_thenReturnEvent() {
    Event retE1 = eventMapper.eventDtoToEvent(eventService.getOneById(E1.getId()));
    Assert.assertThat(retE1, is(equalTo(E1)));
  }

  @Test
  public void givenNoEvent_whenFindEventById_thenThrowNotFoundException() {
    Assertions.assertThrows(NotFoundException.class, () -> eventService.getOneById(-1L));
  }

  @Test
  public void givenEventId_whenFindPerformancesByEventId_thenReturnPerformances() {
    List<PerformanceSearchResultDto> retList =
        eventService.getPerformancesByEventId(E1.getId(), Pageable.unpaged());

    Assert.assertTrue(retList
        .contains(performanceSearchResultMapper.performanceToPerformanceSearchResultDto(P1)));
    Assert.assertTrue(retList
        .contains(performanceSearchResultMapper.performanceToPerformanceSearchResultDto(P2)));

  }

  @Test
  public void givenInvalidEventId_whenFindPerformancesByEventId_thenReturnNoPerformances() {
    List<PerformanceSearchResultDto> retList =
        eventService.getPerformancesByEventId(-1L, Pageable.unpaged());
    Assert.assertThat(retList.size(), is(equalTo(0)));
  }

  @Test
  public void givenEvents_whenFilterByEventName_thenReturnEvents() {

    EventFilterDto filterDto = new EventFilterDto();
    filterDto.setName("D");

    List<EventSearchResultDto> retList = eventService.getFiltered(filterDto, Pageable.unpaged());

    Assert.assertThat(retList.size(), is(2));
    Assert.assertTrue(retList.contains(E2_SR));
    Assert.assertTrue(retList.contains(E3_SR));

  }

  @Test
  public void givenEvents_whenFilterByEventCategory_thenReturnEvents() {

    EventFilterDto filterDto = new EventFilterDto();
    filterDto.setEventCategory(EventCategory.CONCERT);

    List<EventSearchResultDto> retList = eventService.getFiltered(filterDto, Pageable.unpaged());

    Assert.assertThat(retList.size(), is(1));
    Assert.assertTrue(retList.contains(E2_SR));
  }

  @Test
  public void givenEvents_whenFilterByArtistLowerCaseSurname_thenReturnEvents() {

    EventFilterDto filterDto = new EventFilterDto();
    filterDto.setArtistName("abcd");

    List<EventSearchResultDto> retList = eventService.getFiltered(filterDto, Pageable.unpaged());

    Assert.assertThat(retList.size(), is(2));
    Assert.assertTrue(retList.contains(E1_SR));
    Assert.assertTrue(retList.contains(E2_SR));
  }

  @Test
  public void givenEvents_whenFilterByArtistUpperCaseSurname_thenReturnEvents() {

    EventFilterDto filterDto = new EventFilterDto();
    filterDto.setArtistName("F");

    List<EventSearchResultDto> retList = eventService.getFiltered(filterDto, Pageable.unpaged());

    Assert.assertThat(retList.size(), is(2));
    Assert.assertTrue(retList.contains(E2_SR));
    Assert.assertTrue(retList.contains(E3_SR));
  }

  @Test
  public void givenEvents_whenFilterByArtistLowerCaseSurName_thenReturnEvents() {

    EventFilterDto filterDto = new EventFilterDto();
    filterDto.setArtistName("abcd");

    List<EventSearchResultDto> retList = eventService.getFiltered(filterDto, Pageable.unpaged());

    Assert.assertThat(retList.size(), is(2));
    Assert.assertTrue(retList.contains(E1_SR));
    Assert.assertTrue(retList.contains(E2_SR));
  }

  @Test
  public void givenEvents_whenFilterByArtistLowerCaseName_thenReturnEvents() {

    EventFilterDto filterDto = new EventFilterDto();
    filterDto.setArtistName("w");

    List<EventSearchResultDto> retList = eventService.getFiltered(filterDto, Pageable.unpaged());

    Assert.assertThat(retList.size(), is(3));
    Assert.assertTrue(retList.contains(E1_SR));
    Assert.assertTrue(retList.contains(E2_SR));
    Assert.assertTrue(retList.contains(E3_SR));
  }

  @Test
  public void givenEvents_whenFilterByArtistUpperCaseName_thenReturnEvents() {

    EventFilterDto filterDto = new EventFilterDto();
    filterDto.setArtistName("W3");

    List<EventSearchResultDto> retList = eventService.getFiltered(filterDto, Pageable.unpaged());

    Assert.assertThat(retList.size(), is(1));
    Assert.assertTrue(retList.contains(E2_SR));
  }
}
