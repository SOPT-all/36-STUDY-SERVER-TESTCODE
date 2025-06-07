package sopt.study.testcode.minhyuk.spring.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import sopt.study.testcode.minhyuk.spring.unit.beverage.Beverage;
import sopt.study.testcode.minhyuk.spring.unit.order.Order;

@Getter
public class CafeKiosk {


	private static  final LocalTime SHOP_OPEN_TIME = LocalTime.of(10,0);
	private static  final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22,0);


	private final List<Beverage> beverages = new ArrayList<>();

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void add(Beverage beverage, int count){
		if(count <=0){
			throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
		}

		for(int i=0;i<count;i++){
			beverages.add(beverage);
		}
	}

	// public int calculateTotalPrice(){
	// 	return 0;
	// }

	public void remove(Beverage beverage){
		beverages.remove(beverage);
	}

	public void clear(){
		beverages.clear();
	}

	public int calculateTotalPrice() {
		return beverages.stream()
			.mapToInt(Beverage::getPrice)
			.sum();
	}

	public Order createOrder(){
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalTime currentTime = currentDateTime.toLocalTime();
		if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(LocalDateTime.now(),beverages);
	}

	public Order createOrder(LocalDateTime currentDateTime){
		// LocalDateTime currentDateTime = LocalDateTime.now();
		LocalTime currentTime = currentDateTime.toLocalTime();
		if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(LocalDateTime.now(),beverages);
	}
}
