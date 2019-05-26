package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.pricecategory;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PriceCategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import java.awt.Color;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-05-26T10:12:40+0200",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class PriceCategoryMapperImpl implements PriceCategoryMapper {


    @Override
    public PriceCategoryDto priceCategoryToPriceCategoryDto(PriceCategory priceCategory) {
        if ( priceCategory == null ) {
            return null;
        }

        PriceCategoryDto priceCategoryDto = new PriceCategoryDto();

        priceCategoryDto.setId( priceCategory.getId() );
        priceCategoryDto.setPriceInCents( priceCategory.getPriceInCents() );
        priceCategoryDto.setName( priceCategory.getName() );
        priceCategoryDto.setColor( priceCategory.getColor().getRGB() );

        return priceCategoryDto;
    }
}
