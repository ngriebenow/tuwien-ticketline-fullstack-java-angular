package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.pricecategory;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PriceCategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceCategoryMapper {

  PriceCategory priceCategoryDtoToPriceCategory(PriceCategoryDto priceCategoryDto);

  PriceCategoryDto priceCategoryToPriceCategoryDto(PriceCategory priceCategory);
}
