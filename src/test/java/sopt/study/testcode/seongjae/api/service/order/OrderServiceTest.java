package sopt.study.testcode.seongjae.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.seongjae.domain.product.ProductType.BAKERY;
import static sopt.study.testcode.seongjae.domain.product.ProductType.BOTTLE;
import static sopt.study.testcode.seongjae.domain.product.ProductType.HANDMADE;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sopt.study.testcode.seongjae.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.seongjae.api.service.order.response.OrderResponse;
import sopt.study.testcode.seongjae.domain.order.OrderRepository;
import sopt.study.testcode.seongjae.domain.orderproduct.OrderProductRepository;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductRepository;
import sopt.study.testcode.seongjae.domain.product.ProductType;
import sopt.study.testcode.seongjae.domain.stock.Stock;
import sopt.study.testcode.seongjae.domain.stock.StockRepository;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private StockRepository stockRepository;

  @AfterEach
  void tearDown() {
    orderProductRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
    orderRepository.deleteAllInBatch();
    stockRepository.deleteAllInBatch();
  }

  @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
  @Test
  void createOrder() {
    // given
    final Product product1 = createProduct(HANDMADE, "001", 1000);
    final Product product2 = createProduct(HANDMADE, "002", 3000);
    final Product product3 = createProduct(HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));
    final OrderCreateRequest request = OrderCreateRequest.builder()
        .productNumbers(List.of("001", "002"))
        .build();

    // when
    final LocalDateTime registeredDateTime = LocalDateTime.now();
    OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
        .extracting("registeredDateTime", "totalPrice")
        .contains(registeredDateTime, 4000);

    assertThat(orderResponse.getProducts()).hasSize(2)
        .extracting("productNumber", "price")
        .containsExactlyInAnyOrder(
            tuple("001", 1000),
            tuple("002", 3000)
        );
  }

  @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
  @Test
  void createOrderWithDuplicateProductNumbers() {
    // given
    final Product product1 = createProduct(HANDMADE, "001", 1000);
    final Product product2 = createProduct(HANDMADE, "002", 3000);
    final Product product3 = createProduct(HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));
    final OrderCreateRequest request = OrderCreateRequest.builder()
        .productNumbers(List.of("001", "001"))
        .build();

    // when
    final LocalDateTime registeredDateTime = LocalDateTime.now();
    OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
        .extracting("registeredDateTime", "totalPrice")
        .contains(registeredDateTime, 2000);

    assertThat(orderResponse.getProducts()).hasSize(2)
        .extracting("productNumber", "price")
        .containsExactlyInAnyOrder(
            tuple("001", 1000),
            tuple("001", 1000)
        );
  }

  @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
  @Test
  void createOrderWithStock() {
    // given
    final LocalDateTime registeredDateTime = LocalDateTime.now();

    final Product product1 = createProduct(BOTTLE, "001", 1000);
    final Product product2 = createProduct(BAKERY, "002", 3000);
    final Product product3 = createProduct(HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));

    final OrderCreateRequest request = OrderCreateRequest.builder()
        .productNumbers(List.of("001", "001", "002", "003"))
        .build();

    final Stock stock1 = Stock.create("001", 2);
    final Stock stock2 = Stock.create("002", 2);
    stockRepository.saveAll(List.of(stock1, stock2));

    // when
    OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
        .extracting("registeredDateTime", "totalPrice")
        .contains(registeredDateTime, 10000);

    assertThat(orderResponse.getProducts()).hasSize(4)
        .extracting("productNumber", "price")
        .containsExactlyInAnyOrder(
            tuple("001", 1000),
            tuple("001", 1000),
            tuple("002", 3000),
            tuple("003", 5000)
        );

    final List<Stock> stocks = stockRepository.findAll();
    assertThat(stocks).hasSize(2)
        .extracting("productNumber", "quantity")
        .containsExactlyInAnyOrder(
            tuple("001", 0),
            tuple("002", 1)
        );
  }

  @DisplayName("재고가 없는 상품으로 주문을 생성하려고 하는 경우 예외가 발생한다.")
  @Test
  void createOrderWithNoStock() {
    // given
    final LocalDateTime registeredDateTime = LocalDateTime.now();

    final Product product1 = createProduct(BOTTLE, "001", 1000);
    final Product product2 = createProduct(BAKERY, "002", 3000);
    final Product product3 = createProduct(HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));

    final Stock stock1 = Stock.create("001", 1);
    final Stock stock2 = Stock.create("002", 1);
    stockRepository.saveAll(List.of(stock1, stock2));

    final OrderCreateRequest request = OrderCreateRequest.builder()
        .productNumbers(List.of("001", "001", "002", "003"))
        .build();

    // when & then
    assertThatThrownBy(() -> orderService.createOrder(request.toServiceRequest(), registeredDateTime))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("재고가 부족한 상품이 있습니다.");
  }

  private Product createProduct(
      ProductType type,
      String productNumber,
      int price
  ) {
    return Product.builder()
        .type(type)
        .productNumber(productNumber)
        .price(price)
        .sellingStatus(SELLING)
        .name("메뉴 이름")
        .build();
  }

}