package sopt.study.testcode.jaeheon.spring.domain.stock;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class StockTest {

	@DisplayName("재고의 수량이 제공된 수량보다 적은지 확인함")
	@Test
	void isQuantityLessThan(){
	    // given
		Stock stock = Stock.create("001", 1);
		int quantity = 2;

	    // when
		boolean result = stock.isQuantityLessThan(2);

	    // then
		assertThat(result).isTrue();
	}

	@DisplayName("재고를 주어진 개수만큼 차감할 수 있음")
	@Test
	void deductQuantity(){
		// given
		Stock stock = Stock.create("001", 1);
		int quantity = 1;

		// when
		stock.deductQuantity(quantity);

		// then
		assertThat(stock.getQuantity()).isEqualTo(0);
		assertThat(stock.getQuantity()).isZero();
	}

	@DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생함")
	@Test
	void deductQuantity2(){
		// given
		Stock stock = Stock.create("001", 1);
		int quantity = 2;

		// when // then
		assertThatThrownBy(() -> stock.deductQuantity(quantity))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("차감할 재고 수량이 없습니다.");
	}

	// @DisplayName("")
	// @TestFactory // @Test 대신 @TestFactory 를 선언해줌
	// Collection<DynamicTest> dynamicTest(){
	//
	// 	// 원래 여기서 환경을 설정하고
	//
	// 	// 리스트 형태로 DynamicTest 를 던지면서 시나리오를 확인하는 것.
	// 	// 단계별로 행위와 검증을 수행하는 것.
	// 	return List.of( // 리턴 값으로 Collection 이나 Stream 등의 Iterable 한 것들을 던지면 됨
	// 		DynamicTest.dynamicTest("", () -> {
	//
	// 		}),
	// 		DynamicTest.dynamicTest("", () -> {
	//
	// 		})
	// 	);
	// }

	@DisplayName("재고 차감 시나리오")
	@TestFactory
	Collection<DynamicTest> stockDeductionDynamicTest() {
		// given
		Stock stock = Stock.create("001", 1);

		return List.of(
			DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.", () -> {
				// given
				int quantity = 1;

				// when
				stock.deductQuantity(quantity);

				// then
				assertThat(stock.getQuantity()).isZero();
			}),
			DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.", () -> {
				// given
				int quantity = 1;

				// when // then
				assertThatThrownBy(() -> stock.deductQuantity(quantity))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessage("차감할 재고 수량이 없습니다.");
			})
		);
	}


}