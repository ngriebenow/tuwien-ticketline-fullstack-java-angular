package at.ac.tuwien.sepm.groupphase.backend.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.entity.PriceCategory;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PriceCategoryRepository;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PriceCategoryRepositoryTest {

  private PriceCategory PRICE_CATEGORY_1 =
      new PriceCategory.Builder()
          .name("Kategorie 1")
          .priceInCents(10101)
          .color(new Color(145, 33, 0))
          .build();

  @Autowired PriceCategoryRepository priceCategoryRepository;

  @Before
  public void setUp() {
    priceCategoryRepository.save(PRICE_CATEGORY_1);
  }

  @Test
  public void givenPriceCategorySaved_whenFindPriceCategoryById_thenReturnPriceCategory() {
    PriceCategory priceCategory = priceCategoryRepository.findById(PRICE_CATEGORY_1.getId()).get();
    assertThat(priceCategory).isEqualTo(PRICE_CATEGORY_1);
  }

  @Test(expected = NotFoundException.class)
  public void
      givenPriceCategorySaved_whenFindUnknownPriceCategoryById_thenThrowNotFoundException()
      throws NotFoundException {
    priceCategoryRepository.findById(-1L).orElseThrow(NotFoundException::new);
  }
}
