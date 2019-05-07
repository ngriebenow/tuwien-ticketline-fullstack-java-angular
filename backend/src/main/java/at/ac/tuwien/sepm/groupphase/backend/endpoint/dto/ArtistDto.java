package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.SimpleMessageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ApiModel(value = "ArtistDto", description = "A DTO for an artist via rest")
public class ArtistDto {

  @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
  private Long id;

  @ApiModelProperty(required = true, name = "The first name of the artist")
  private String name;

  @ApiModelProperty(required = true, name = "The last name of the artist")
  private String surname;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  /** Build the artist dto */
  public ArtistDto build() {
    ArtistDto artistDto = new ArtistDto();
    artistDto.setId(id);
    artistDto.setName(name);
    artistDto.setSurname(surname);
    return artistDto;
  }

}
