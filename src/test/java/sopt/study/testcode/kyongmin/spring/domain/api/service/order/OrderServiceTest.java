package sopt.study.testcode.kyongmin.spring.domain.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static sopt.study.testcode.kyongmin.spring.domain.product.ProductSellingStatus.*;
import static sopt.study.testcode.kyongmin.spring.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.request.OrderCreateRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.response.OrderResponse;
import sopt.study.testcode.kyongmin.spring.domain.order.OrderRepository;
import sopt.study.testcode.kyongmin.spring.domain.orderproduct.OrderProductRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.Product;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductType;

@ActiveProfiles("test")
@SpringBootTest
// @DataJpaTest -> orderservice빈이 생성되지 않음
class OrderServiceTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private OrderService orderService;

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();

		// orderProductRepository.deleteAll();
		// productRepository.deleteAll();
		// orderRepository.deleteAll();
	}

	@Test
	@DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
	void createOrder() {
		//given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 1000);
		Product product2 = createProduct(HANDMADE, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "002"))
			.build();

		// when
		OrderResponse response = orderService.createOrder(request, registeredDateTime);

		//then
		assertThat(response.getId()).isNotNull();
		assertThat(response)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 4000);
		assertThat(response.getProducts()).hasSize(2)
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
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 1000);
		Product product2 = createProduct(HANDMADE, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "001"))
			.build();

		// when
		OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

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

	// 테스트에 필요한 값만 유동적으로 생성할 수 있는 생성 메서드를 별도로 구현하는 것도 좋은 방법
	private Product createProduct(ProductType productType, String productNumber, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(productType)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}