package sopt.study.testcode.jaeheon.spring.domain.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {


	@DisplayName("상품 타입이 재고를 확인해야하는 타입인지를 체그함")
	@Test
	void containsStockType(){
	    // given
		ProductType handmade = ProductType.HANDMADE;

	    // when
		boolean result = ProductType.containsStockType(handmade);

		// then
		assertThat(result).isFalse();
	}

	@DisplayName("상품 타입이 재고를 확인해야하는 타입인지를 체그함")
	@Test
	void containsStockType2(){
		// given
		ProductType bakery = ProductType.BAKERY;

		// when
		boolean result = ProductType.containsStockType(bakery);

		// then
		assertThat(result).isTrue();
	}

}