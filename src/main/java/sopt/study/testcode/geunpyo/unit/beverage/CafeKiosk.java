package sopt.study.testcode.geunpyo.unit.beverage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.cglib.core.Local;

import lombok.Getter;
import sopt.study.testcode.geunpyo.unit.order.Order;

public class CafeKiosk {
	@Getter
	private final List<Beverage> beverages = new ArrayList<>();
	private final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
	private final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void add(Beverage beverage, int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
		}
		for (int i = 0; i < count; i++) {
			beverages.add(beverage);
		}
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}


	public void clear() {
		beverages.clear();
	}

	public int calculateTotalPrice() {
		int totalPrice = 0;
		for (Beverage beverage : beverages) {
			totalPrice += beverage.getPrice();
		}
		return totalPrice;
	}

	public Order createOrder(LocalDateTime currentDateTime) {
		// LocalDateTime currentDateTime = LocalDateTime.now();
		LocalTime currentTime = currentDateTime.toLocalTime();

		if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(LocalDateTime.now(), beverages);
	}
}
