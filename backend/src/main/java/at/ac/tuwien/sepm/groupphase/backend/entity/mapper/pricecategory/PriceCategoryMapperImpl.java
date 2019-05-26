package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.pricecategory;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PriceCategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;


@Component
public class PriceCategoryMapperImpl implements PriceCategoryMapper {


  @Override
  public PriceCategoryDto priceCategoryToPriceCategoryDto(PriceCategory priceCategory) {
    if (priceCategory == null) {
      return null;
    }

    PriceCategoryDto priceCategoryDto = new PriceCategoryDto();

    priceCategoryDto.setId(priceCategory.getId());
    priceCategoryDto.setPriceInCents(priceCategory.getPriceInCents());
    priceCategoryDto.setName(priceCategory.getName());
    priceCategoryDto.setColor(priceCategory.getColor().getRGB());

    return priceCategoryDto;
  }
}
