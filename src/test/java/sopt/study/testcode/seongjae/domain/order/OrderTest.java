package sopt.study.testcode.seongjae.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.SELLING;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductType;

class OrderTest {

  @DisplayName("주문 생성 시 주문 상태는 INIT 이다.")
  @Test
  void init() {
    // given
    final List<Product> products = List.of(
        createProduct("001", 1000),
        createProduct("002", 2000)
    );
    // when
    Order order = Order.create(products, LocalDateTime.now());

    // then
    assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
  }


  @DisplayName("주문 생성 시에 상품 리스트 에서 주문의 총 금액을 계산한다.")
  @Test
  void calculateTotalPrice() {
    // given
    final List<Product> products = List.of(
        createProduct("001", 1000),
        createProduct("002", 2000)
    );
    // when
    Order order = Order.create(products, LocalDateTime.now());

    // then
    assertThat(order.getTotalPrice()).isEqualTo(3000);
  }

  @DisplayName("주문 생성 시에 주문 등록 시간을 기록한다.")
  @Test
  void registeredDateTime() {
    // given
    final List<Product> products = List.of(
        createProduct("001", 1000),
        createProduct("002", 2000)
    );
    // when
    final LocalDateTime registeredDateTime = LocalDateTime.now();
    Order order = Order.create(products, registeredDateTime);

    // then
    assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
  }


  private Product createProduct(
      String productNumber,
      int price
  ) {
    return Product.builder()
        .type(ProductType.HANDMADE)
        .productNumber(productNumber)
        .price(price)
        .sellingStatus(SELLING)
        .name("메뉴 이름")
        .build();
  }

}