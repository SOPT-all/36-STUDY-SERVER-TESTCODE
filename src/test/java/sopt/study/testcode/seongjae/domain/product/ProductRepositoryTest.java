package sopt.study.testcode.seongjae.domain.product;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.HOLD;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.STOP_SELLING;
import static sopt.study.testcode.seongjae.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
  @Test
  void findAllBySellingStatusIn() {
    // given
    final Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    final Product product2 = createProduct("002", HANDMADE, HOLD, "카페라테", 4500);
    final Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);

    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    final List<Product> products = productRepository.findAllBySellingStatusIn(
        List.of(SELLING, HOLD));

    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingStatus")
        .containsExactlyInAnyOrder(
            tuple("001", "아메리카노", SELLING),
            tuple("002", "카페라테", HOLD)
        );
  }

  @DisplayName("상품 번호 리스트로 상품들을 조회한다.")
  @Test
  void findAllByProductNumberIn() {

    // given
    final Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    final Product product2 = createProduct("002", HANDMADE, HOLD, "까페라테", 4500);
    final Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);

    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    final List<Product> products = productRepository.findAllByProductNumberIn(
        List.of("001", "002")
    );

    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingStatus")
        .containsExactlyInAnyOrder(
            tuple("001", "아메리카노", SELLING),
            tuple("002", "까페라테", HOLD)
        );

  }


  @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
  @Test
  void findLatestProductNumber() {

    // given
    final String targetProductNumber = "003";

    final Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    final Product product2 = createProduct("002", HANDMADE, HOLD, "카페라테", 4500);
    final Product product3 = createProduct(targetProductNumber, HANDMADE, STOP_SELLING, "팥빙수",
        7000);

    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    final String latestProductNumber = productRepository.findLatestProductNumber();

    // then
    assertThat(latestProductNumber).isEqualTo(targetProductNumber);
  }

  @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우에는 null을 반환한다.")
  @Test
  void findLatestProductNumberWhenProductIsEmpty() {

    // when
    final String latestProductNumber = productRepository.findLatestProductNumber();

    // then
    assertThat(latestProductNumber).isNull();
  }

  private Product createProduct(String productNumber, final ProductType type,
      final ProductSellingStatus sellingStatus, final String name, final int price) {
    return Product.builder()
        .productNumber(productNumber)
        .type(type)
        .sellingStatus(sellingStatus)
        .name(name)
        .price(price)
        .build();
  }

}