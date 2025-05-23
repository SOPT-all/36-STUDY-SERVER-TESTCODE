package sopt.study.testcode.hyunjin.spring.api.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sopt.study.testcode.hyunjin.spring.api.controller.order.OrderCreateRequest;
import sopt.study.testcode.hyunjin.spring.api.service.order.response.OrderResponse;
import sopt.study.testcode.hyunjin.spring.domain.product.Product;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductRepository;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;


@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    void createOrder() {
        LocalDateTime registeredDateTime = LocalDateTime.now();

        // given
        Product p1 = createProduct("001", ProductType.HANDMADE, 1000);
        Product p2 = createProduct("002", ProductType.BAKERY, 3000);
        Product p3 = createProduct("003", ProductType.BOTTLE, 4000);
        productRepository.saveAll(List.of(p1, p2, p3));

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest, registeredDateTime);

        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 4000);
        assertThat(orderResponse.products()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("002", 3000)
                );
    }

    private Product createProduct(String productNumber, ProductType productType, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(productType)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("음료")
                .price(price)
                .build();
    }


}