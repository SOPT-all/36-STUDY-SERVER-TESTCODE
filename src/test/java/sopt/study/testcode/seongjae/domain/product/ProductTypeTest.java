package sopt.study.testcode.seongjae.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

  @DisplayName("상품 타입이 재고관련 타입인지를 체크한다.")
  @Test
  void containsStockType() {
    // given
    final List<ProductType> givenTypes = List.of(ProductType.BOTTLE, ProductType.BAKERY);

    // when
    List<Boolean> results = givenTypes.stream()
        .map(ProductType::containsStockType)
        .toList();

    // then
    assertThat(results)
        .containsOnly(true);
  }

  @DisplayName("제조음료는 재고 타입이 아니다.")
  @Test
  void containsStockType2() {
    // given
    final ProductType givenType = ProductType.HANDMADE;

    // when
    final boolean result = ProductType.containsStockType(givenType);

    // then
    assertThat(result).isFalse();
  }
}