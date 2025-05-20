package sopt.study.testcode.kyongmin.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static sopt.study.testcode.kyongmin.spring.domain.product.ProductSellingStatus.*;
import static sopt.study.testcode.kyongmin.spring.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sopt.study.testcode.kyongmin.spring.domain.product.Product;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductType;

class OrderTest {
	@Test
	@DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
	void calculateTotalPrice() {
		// given
		List<Product> products = List.of(
			createProduct("001", 1000),
			createProduct("002", 2000)
		);

		// when
		Order order = Order.create(products, LocalDateTime.now());

		// then
		assertThat(order.getTotalPrice()).isEqualTo(3000);
	}

	@DisplayName("주문 생성 시 주문 상태는 INIT이다.")
	@Test
	void init() {
		// given
		List<Product> products = List.of(
			createProduct("001", 1000),
			createProduct("002", 2000)
		);

		// when
		Order order = Order.create(products, LocalDateTime.now());

		// then
		assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
	}

	private Product createProduct(String productNumber, int price) {
		return Product.builder()
			.type(HANDMADE)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}