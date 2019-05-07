package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class DefinedUnitDto {

  private Long id;

  private int free;

  private PointDto lowerBoundary;

  private PointDto upperBoundary;

  private Long priceCategoryId;

  private PriceCategoryDto priceCategoryDto;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getFree() {
    return free;
  }

  public void setFree(int free) {
    this.free = free;
  }

  public PointDto getLowerBoundary() {
    return lowerBoundary;
  }

  public void setLowerBoundary(PointDto lowerBoundary) {
    this.lowerBoundary = lowerBoundary;
  }

  public PointDto getUpperBoundary() {
    return upperBoundary;
  }

  public void setUpperBoundary(PointDto upperBoundary) {
    this.upperBoundary = upperBoundary;
  }

  public Long getPriceCategoryId() {
    return priceCategoryId;
  }

  public void setPriceCategoryId(Long priceCategoryId) {
    this.priceCategoryId = priceCategoryId;
  }

  public PriceCategoryDto getPriceCategoryDto() {
    return priceCategoryDto;
  }

  public void setPriceCategoryDto(PriceCategoryDto priceCategoryDto) {
    this.priceCategoryDto = priceCategoryDto;
  }
}
