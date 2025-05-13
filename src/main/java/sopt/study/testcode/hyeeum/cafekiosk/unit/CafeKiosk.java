package sopt.study.testcode.hyeeum.cafekiosk.unit;

import lombok.Getter;
import sopt.study.testcode.hyeeum.cafekiosk.unit.beverage.Beverage;
import sopt.study.testcode.hyeeum.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// 요구사항 충족 객체
@Getter
public class CafeKiosk {
    public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문 가능합니다.");
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
//        return 0; // 실패 코드 -> RED
//        return 8500; // 최소한의 코드 -> GREEN

// REFACTOR
        int totalPrice = 0;
        for (Beverage beverage : beverages) {
            totalPrice += beverage.getPrice();
        }
        return totalPrice;
    }

    public Order createOrder() {
        LocalDateTime currentDateTime = LocalDateTime.now(); // 실행마다 달라져서 문제가 된다. 그러므로 밖에서 받아와야한다.
        LocalTime currentTime = currentDateTime.toLocalTime();

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의바람 ㅋㅋ");
        }

        return new Order(LocalDateTime.now(), beverages);
    }

    public Order createOrder(LocalDateTime localDateTime) {
        LocalTime currentTime = localDateTime.toLocalTime();

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의바람 ㅋㅋ");
        }

        return new Order(LocalDateTime.now(), beverages);
    }
}
