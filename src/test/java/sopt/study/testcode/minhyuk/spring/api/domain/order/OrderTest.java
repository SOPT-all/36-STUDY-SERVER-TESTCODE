package sopt.study.testcode.minhyuk.spring.api.domain.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sopt.study.testcode.minhyuk.spring.api.domain.product.Product;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductSellingType;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductType;

class OrderTest {
	@DisplayName("상품 리스트에서 주문의 총 금액을 계산한다.")
	@Test
	void calculateTotalPrice(){
	    //given
		List<Product> products = List.of(
			createProduct("001",1000),
			createProduct("002",2000)
		);


	    //when
		Order order =Order.create(products,LocalDateTime.now());

	    //then
		assertThat(order.getTotalPrice()).isEqualTo(3000);


	 }


	@DisplayName("주문생성시 처음.")
	@Test
	void inti(){
		//given
		List<Product> products = List.of(
			createProduct("001",1000),
			createProduct("002",2000)
		);


		//when
		Order order =Order.create(products, LocalDateTime.now());

		//then
		assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);


	}


	@DisplayName("주문생성 시 주문 등록 시간을 기록한다.")
	@Test
	void registerDateTime(){
		//given
		LocalDateTime localDateTime = LocalDateTime.now();
		List<Product> products = List.of(
			createProduct("001",1000),
			createProduct("002",2000)
		);


		//when
		Order order =Order.create(products, LocalDateTime.now());

		//then
		assertThat(order.getRegisteredDateTime()).isEqualTo(localDateTime);


	}

	private Product createProduct( String productNumber, int price){
		return Product.builder()
			.type(ProductType.HANDMADE)
			.productNumber(productNumber)
			.price(price)
			.selling_status(ProductSellingType.SELLING)
			.name("메뉴 이름")
			.build();

	}




}