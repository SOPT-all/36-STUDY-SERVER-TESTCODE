package sopt.study.testcode.jaeheon.spring.domain.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다")
	@Test
	void test(){
	    // given
		ProductType givenType1 = ProductType.HANDMADE;
		ProductType givenType2 = ProductType.BOTTLE;
		ProductType givenType3 = ProductType.BAKERY;

	    // when
	    boolean result1 = ProductType.containsStockType(givenType1);
		boolean result2 = ProductType.containsStockType(givenType2);
		boolean result3 = ProductType.containsStockType(givenType3);

	    // then
		assertThat(result1).isFalse();
		assertThat(result2).isTrue();
		assertThat(result3).isTrue();
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
	@ParameterizedTest
	void containsStockType4(ProductType productType, boolean expected) {
		// when
		boolean result = ProductType.containsStockType(productType);

		// then
		assertThat(result).isEqualTo(expected);
	}

	// Stream으로 Arguments를 반환하고 이 메서드를 소스로 넣어줌.
	private static Stream<Arguments> provideProductTypesForCheckingStockType() {
		return Stream.of(
			Arguments.of(ProductType.HANDMADE, false),
			Arguments.of(ProductType.BOTTLE, true),
			Arguments.of(ProductType.BAKERY, true)
		);
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@MethodSource("provideProductTypesForCheckingStockType")
	@ParameterizedTest
	void containsStockType5(ProductType productType, boolean expected) {
		// when
		boolean result = ProductType.containsStockType(productType);

		// then
		assertThat(result).isEqualTo(expected);
	}



	// @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	// @CsvSource({"HANDMADE,false","BOTTLE,true","BAKERY,true"})
	// @ParameterizedTest
	// void containsStockType4(ProductType productType, boolean expected) {
	// 	// when
	// 	boolean result = ProductType.containsStockType(productType);
	//
	// 	// then
	// 	assertThat(result).isEqualTo(expected);
	// }
	//
	// private static Stream<Arguments> provideProductTypesForCheckingStockType() {
	// 	return Stream.of(
	// 		Arguments.of(ProductType.HANDMADE, false),
	// 		Arguments.of(ProductType.BOTTLE, true),
	// 		Arguments.of(ProductType.BAKERY, true)
	// 	);
	// }
	//
	// @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	// @MethodSource("provideProductTypesForCheckingStockType")
	// @ParameterizedTest
	// void containsStockType5(ProductType productType, boolean expected) {
	// 	// when
	// 	boolean result = ProductType.containsStockType(productType);
	//
	// 	// then
	// 	assertThat(result).isEqualTo(expected);
	// }


}