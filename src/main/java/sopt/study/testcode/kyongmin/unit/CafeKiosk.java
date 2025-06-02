package sopt.study.testcode.kyongmin.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import sopt.study.testcode.kyongmin.unit.beverage.Beverage;
import sopt.study.testcode.kyongmin.unit.order.Order;

@Getter
public class CafeKiosk {
	private static final LocalTime OPENING_TIME = LocalTime.of(10,0);
	private static final LocalTime CLOSING_TIME = LocalTime.of(22,0);

	private final List<Beverage> beverages = new ArrayList<>();

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void add(Beverage beverage, int count){
		if(count <=0){
			throw new IllegalArgumentException("음료는 1잔 이상만 주문할 수 있습니다.");
		}
		for (int i = 0; i < count; i++) {
			beverages.add(beverage);
		}
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public void removeAll() {
		beverages.clear();
	}

	public int calculatePrice() {
		return beverages.stream()
			.mapToInt(Beverage::getPrice)
			.sum();
	}

	public Order createOrder(LocalDateTime now){
		// 운영시간 오전10부터 오후 10시 사이가 아니면 예외처리
		// LocalDateTime now = LocalDateTime.now();
		LocalTime currentTime = now.toLocalTime();
		if(currentTime.isBefore(OPENING_TIME)||currentTime.isAfter(CLOSING_TIME)){
			throw new IllegalArgumentException("주문 가능한 시간이 아닙니다.");
		}
		return new Order(now, beverages);
	}
}
