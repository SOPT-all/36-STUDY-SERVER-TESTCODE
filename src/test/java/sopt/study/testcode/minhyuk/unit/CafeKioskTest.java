package sopt.study.testcode.minhyuk.unit;


import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sopt.study.testcode.minhyuk.spring.unit.CafeKiosk;
import sopt.study.testcode.minhyuk.spring.unit.beverage.Americano;
import sopt.study.testcode.minhyuk.spring.unit.beverage.Latte;
import sopt.study.testcode.minhyuk.spring.unit.order.Order;

class CafeKioskTest {

	@Test
	void 음료_추가_테스트() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
		System.out.println(">>> 담긴 음료 : " +  cafeKiosk.getBeverages().get(0).getName());

	}
	@DisplayName("음료 1개를 추가하면 주문 목록에 담긴다.")
	@Test
	void add(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
		assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");

	}

	@Test
	void addSeveralBeverages(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano =new Americano();

		cafeKiosk.add(americano,2);


		assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
		assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);

	}


	@Test
	void addZeroBeverages(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano =new Americano();


		assertThatThrownBy(()->cafeKiosk.add(americano,0))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");

	}

	@Test
	void remove(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);
		assertThat(cafeKiosk.getBeverages()).hasSize(1);

		cafeKiosk.remove(americano);
		assertThat(cafeKiosk.getBeverages()).isEmpty();
		assertThat(cafeKiosk.getBeverages()).hasSize(0);

	}
	@Test
	void clear(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);
		assertThat(cafeKiosk.getBeverages()).hasSize(2);

		cafeKiosk.clear();
		assertThat(cafeKiosk.getBeverages()).isEmpty();

	}
	@DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
	@Test
	void calculateTotalPrice(){
		//Given
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();


		cafeKiosk.add(americano);
		cafeKiosk.add(latte);

		//when -> 보통 수행을 하는 단계
		int totalPrice = cafeKiosk.calculateTotalPrice();
		//then
		assertThat(totalPrice).isEqualTo(8500);
	}

	// @Test
	// void createOrder(){
	// 	CafeKiosk cafeKiosk = new CafeKiosk();
	// 	Americano americano = new Americano();
	// 	cafeKiosk.add(americano);
	//
	// 	Order order = cafeKiosk.createOrder();
	// 	assertThat(order.getBeverages()).hasSize(1);
	// 	assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	// }

	@Test
	void createOrderWithCurrentTIme(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.add(americano);

		Order order = cafeKiosk.createOrder(LocalDateTime.of(2025,5,2,14,0));
		assertThat(order.getBeverages()).hasSize(1);
		assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}



	@Test
	void createOrderWithOutsideOpenTIme(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.add(americano);


		assertThatThrownBy(()->cafeKiosk.createOrder(LocalDateTime.of(2025,5,2,9,59)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");

	}
}