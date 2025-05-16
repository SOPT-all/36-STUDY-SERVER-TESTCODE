package sopt.study.testcode.yeongju.spring.api.service.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sopt.study.testcode.yeongju.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.yeongju.spring.api.service.order.response.OrderResponse;
import sopt.study.testcode.yeongju.spring.domain.product.Product;
import sopt.study.testcode.yeongju.spring.domain.product.ProductRepository;
import sopt.study.testcode.yeongju.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sopt.study.testcode.yeongju.spring.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.yeongju.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
//@DataJpaTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull(); // id는 비어있지만 않음 됨
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice") //등록 시간, 총 가격 검증
                .contains(registeredDateTime, 4000);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("002", 3000)
                );
    }

    /*
    프로덕트를 만드는 도우미 메서드 (Builder 를 바깥으로 뺌)
     */
    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }

}