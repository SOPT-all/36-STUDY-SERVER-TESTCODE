package sopt.study.testcode.kyongmin.spring.domain.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static sopt.study.testcode.kyongmin.spring.domain.product.ProductSellingStatus.*;
import static sopt.study.testcode.kyongmin.spring.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import sopt.study.testcode.kyongmin.spring.domain.product.Product;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductType;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("원하는 기간 내에 특정 주문 상태의 주문들을 조회한다.")
	void findOrdersBy() {
		// given
		Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Order order = Order.create(List.of(product1, product2, product3), LocalDateTime.of(2025, 6, 23, 17, 0));
		orderRepository.save(order);

		// when
		List<Order> orders = orderRepository.findOrdersBy(LocalDateTime.of(2025, 6, 23, 10, 0),
				LocalDateTime.of(2025, 6, 23, 18, 0), OrderStatus.INIT);

		// then
		assertThat(orders).hasSize(1)
				.extracting("registeredDateTime", "totalPrice", "orderStatus")
				.containsExactlyInAnyOrder(
						tuple(LocalDateTime.of(2025, 6, 23, 17, 0), 15500 , OrderStatus.INIT)
				);
	}

	private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus,
			String name, int price) {
		return Product.builder()
				.productNumber(productNumber)
				.type(type)
				.sellingStatus(sellingStatus)
				.name(name)
				.price(price)
				.build();
	}
}