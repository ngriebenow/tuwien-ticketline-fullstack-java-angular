package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.LocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.LocationFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
@Api("locations")
public class LocationEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocationEndpoint.class);
  private LocationService locationService;

  @Autowired
  public LocationEndpoint(LocationService locationService) {
    this.locationService = locationService;
  }

  /**
   * Get locations filtered.
   *
   * @param name that filtered locations must match
   * @param street that filtered locations must match
   * @param postalCode that filtered locations must match
   * @param place that filtered locations must match
   * @param country that filtered locations must match
   * @param page that filtered locations must match
   * @param count that filtered locations must match
   * @return list of locations matching the filter parameters
   */
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Get locations filtered",
      authorizations = {@Authorization("apiKey")})
  public List<LocationDto> get(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String street,
      @RequestParam(required = false) String postalCode,
      @RequestParam(required = false) String place,
      @RequestParam(required = false) String country,
      @RequestParam @NotNull @Min(0L) Integer page,
      @RequestParam @NotNull @Min(1L) Integer count
  ) {
    LOGGER.info("Get locations filtered");

    Pageable pageable = PageRequest.of(page, count);
    LocationFilterDto locationFilterDto =
        new LocationFilterDto.Builder()
            .name(name)
            .street(street)
            .postalCode(postalCode)
            .place(place)
            .country(country)
            .build();

    return locationService.getFiltered(locationFilterDto, pageable);
  }
}
