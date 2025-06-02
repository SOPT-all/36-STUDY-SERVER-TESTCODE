package sopt.study.testcode.seongjae.api.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.seongjae.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sopt.study.testcode.seongjae.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.seongjae.api.service.product.response.ProductResponse;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductRepository;
import sopt.study.testcode.seongjae.domain.product.ProductSellingStatus;
import sopt.study.testcode.seongjae.domain.product.ProductType;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  @AfterEach
  void tearDown() {
    productRepository.deleteAllInBatch();
  }

  @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품의 번호에서 1증가한 값이다.")
  @Test
  void createProduct() {
    // given
    final Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    productRepository.save(product1);

    final ProductCreateRequest request = ProductCreateRequest.builder()
        .type(HANDMADE)
        .sellingStatus(SELLING)
        .name("카푸치노")
        .price(5000)
        .build();

    // when
    final ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

    // then
    assertThat(productResponse)
        .extracting("productNumber", "type", "sellingStatus", "name", "price")
        .contains("002", HANDMADE, SELLING, "카푸치노", 5000);

    final List<Product> products = productRepository.findAll();
    assertThat(products).hasSize(2)
        .extracting("productNumber", "type", "sellingStatus", "name", "price")
        .containsExactlyInAnyOrder(
            tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
            tuple("002", HANDMADE, SELLING, "카푸치노", 5000)
        );
  }

  @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
  @Test
  void createProductWhenProductIsEmpty() {
    // given
    final ProductCreateRequest request = ProductCreateRequest.builder()
        .type(HANDMADE)
        .sellingStatus(SELLING)
        .name("카푸치노")
        .price(5000)
        .build();

    // when
    final ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

    // then
    assertThat(productResponse)
        .extracting("productNumber", "type", "sellingStatus", "name", "price")
        .contains("001", HANDMADE, SELLING, "카푸치노", 5000);

    final List<Product> products = productRepository.findAll();
    assertThat(products).hasSize(1)
        .extracting("productNumber", "type", "sellingStatus", "name", "price")
        .containsExactlyInAnyOrder(
            tuple("001", HANDMADE, SELLING, "카푸치노", 5000)
        );
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