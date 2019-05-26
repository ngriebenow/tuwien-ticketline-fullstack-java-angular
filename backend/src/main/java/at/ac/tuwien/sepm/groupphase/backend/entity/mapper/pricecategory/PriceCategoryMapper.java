package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.pricecategory;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PriceCategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;

public interface PriceCategoryMapper {

  PriceCategoryDto priceCategoryToPriceCategoryDto(PriceCategory priceCategory);
}
