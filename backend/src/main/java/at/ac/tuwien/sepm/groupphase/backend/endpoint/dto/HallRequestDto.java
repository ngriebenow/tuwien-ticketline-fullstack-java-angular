package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value = "HallRequestDto", description = "A DTO for a hall request via rest")
public class HallRequestDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The version of the hall")
  private int version;

  @NotBlank(message = "name must not be empty")
  @ApiModelProperty(required = true, name = "The name of the hall")
  private String name;

  @NotNull(message = "location must be set")
  @Valid
  @ApiModelProperty(required = true, name = "The location of the hall")
  private LocationDto location;

  @NotNull(message = "boundaryPoint must be set")
  @Valid
  @JsonProperty("boundaryPoint")
  @ApiModelProperty(
      required = true,
      name = "The boundaryPoint which defines the maximum size of the hall")
  private PointDto boundaryPoint;

  @NotEmpty
  @ApiModelProperty(required = true, name = "The units contained in the hall")
  private List<@Valid UnitDto> units;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocationDto getLocation() {
    return location;
  }

  public void setLocation(LocationDto location) {
    this.location = location;
  }

  public PointDto getBoundaryPoint() {
    return boundaryPoint;
  }

  public void setBoundaryPoint(PointDto boundaryPoint) {
    this.boundaryPoint = boundaryPoint;
  }

  public List<UnitDto> getUnits() {
    return units;
  }

  public void setUnits(List<UnitDto> units) {
    this.units = units;
  }

  /**
   * Build the hallRequest dto.
   */
  public HallRequestDto build() {
    HallRequestDto hallRequestDto = new HallRequestDto();
    hallRequestDto.setId(id);
    hallRequestDto.setBoundaryPoint(boundaryPoint);
    hallRequestDto.setName(name);
    hallRequestDto.setVersion(version);
    hallRequestDto.setUnits(units);
    return hallRequestDto;
  }

  public HallRequestDto() {

  }

  private HallRequestDto(Builder builder) {
    setId(builder.id);
    setVersion(builder.version);
    setName(builder.name);
    setBoundaryPoint(builder.boundaryPoint);
    setLocation(builder.location);
    setUnits(builder.units);
  }

  public static final class Builder {

    private Long id;
    private int version;
    private String name;
    private PointDto boundaryPoint;
    private LocationDto location;
    private List<UnitDto> units;

    public Builder() {
    }

    public Builder id(Long val) {
      id = val;
      return this;
    }

    public Builder version(int val) {
      version = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder boundaryPoint(PointDto val) {
      boundaryPoint = val;
      return this;
    }

    public Builder location(LocationDto val) {
      location = val;
      return this;
    }

    public Builder units(List<UnitDto> val) {
      units = val;
      return this;
    }

    public HallRequestDto build() {
      return new HallRequestDto(this);
    }
  }
}
