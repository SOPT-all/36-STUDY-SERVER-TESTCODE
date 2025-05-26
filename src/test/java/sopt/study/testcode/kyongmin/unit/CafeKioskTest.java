package sopt.study.testcode.kyongmin.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import sopt.study.testcode.kyongmin.unit.CafeKiosk;
import sopt.study.testcode.kyongmin.unit.beverage.Americano;
import sopt.study.testcode.kyongmin.unit.beverage.Latte;
import sopt.study.testcode.kyongmin.unit.order.Order;

class CafeKioskTest {

	@Test
	void add() {
		CafeKiosk cafeKiosk = new CafeKiosk();

		cafeKiosk.add(new Americano());

		assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
		assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	void addSevaralBeverages() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano, 3);

		// 동일한 인스턴스가 추가되는지 확인
		assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
		assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);

		// 올바른 갯수가 추가되는지 확인
		assertThat(cafeKiosk.getBeverages().size()).isEqualTo(3);
	}

	@Test
	void addZeroBeverage() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		/*
		 * 경계값 0에 대한 예외 테스트
		 * 예외 종류와 예외 메시지 내용에 대한 검증
		 */
		assertThatThrownBy(()->cafeKiosk.add(americano, 0))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("음료는 1잔 이상만 주문할 수 있습니다.");
	}

	@Test
	void remove() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);
		cafeKiosk.remove(americano);

		assertThat(cafeKiosk.getBeverages()).hasSize(1);
	}

	@Test
	void removeAll() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);
		cafeKiosk.removeAll();

		assertThat(cafeKiosk.getBeverages()).isEmpty();
	}

	@Test
	void createOrder() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();
		LocalDateTime orderHours = LocalDateTime.of(2025, 4,23,22,0);

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);

		Order order = cafeKiosk.createOrder(orderHours);

		assertThat(order.getBeverages()).hasSize(2);
		assertThat(order.getBeverages().get(0)).isEqualTo(americano);
		assertThat(order.getBeverages().get(1)).isEqualTo(latte);
		// 이 위까지만 하면 주문시간에 따라 테스트가 실패할 수도 있음, 가게 운영시간에만 성공하는 테스트
	}

	@Test
	void createOrderWithClosedTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();
		LocalDateTime closedHours = LocalDateTime.of(2025, 4,22,9,59);

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);

		// 테스트 용이성을 위해 LocalDateTime.now에 대한 의존성을 줄이고자 해당 값을 외부에서 입력받도록 해야함
		assertThatThrownBy(()->cafeKiosk.createOrder(closedHours))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("주문 가능한 시간이 아닙니다.");
	}

}