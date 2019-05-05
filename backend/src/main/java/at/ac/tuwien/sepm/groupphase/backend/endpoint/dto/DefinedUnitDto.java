package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class DefinedUnitDto {

  private long id;

  private int free;

  private PointDto lowerBoundary;

  private PointDto upperBoundary;

  private long priceCategoryId;

  private PriceCategoryDto priceCategoryDto;

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public long getPriceCategoryId() {
    return priceCategoryId;
  }

  public void setPriceCategoryId(long priceCategoryId) {
    this.priceCategoryId = priceCategoryId;
  }

  public PriceCategoryDto getPriceCategoryDto() {
    return priceCategoryDto;
  }

  public void setPriceCategoryDto(PriceCategoryDto priceCategoryDto) {
    this.priceCategoryDto = priceCategoryDto;
  }
}
