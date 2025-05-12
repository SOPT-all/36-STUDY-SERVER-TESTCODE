package sopt.study.testcode.hyunjin.unit;

import lombok.Getter;
import sopt.study.testcode.hyunjin.unit.common.ResponseError;
import sopt.study.testcode.hyunjin.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {
    public static final LocalTime OPEN_TIME = LocalTime.of(10, 0);
    public static final LocalTime CLOSE_TIME = LocalTime.of(21, 59);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(ResponseError.INVALID_QUANTITY.getMessage());
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
//        for (Beverage beverage : beverages) {
//            totalPrice += beverage.getPrice();
//        }
        return totalPrice;
    }

    public Order createOrder() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        if (currentTime.isBefore(OPEN_TIME) || currentTime.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException(ResponseError.OUT_OF_BUSINESS_HOURS.getMessage());
        }

        return new Order(now, beverages);
    }

    public Order createOrder(LocalDateTime orderDateTime) {
        LocalTime currentTime = orderDateTime.toLocalTime();
        if (currentTime.isBefore(OPEN_TIME) || currentTime.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException(ResponseError.OUT_OF_BUSINESS_HOURS.getMessage());
        }

        return new Order(orderDateTime, beverages);
    }
}
