package sopt.study.testcode.seongjae.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import sopt.study.testcode.seongjae.unit.beverage.Beverage;
import sopt.study.testcode.seongjae.unit.order.Order;

public class CafeKiosk {

  public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
  public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

  private final List<Beverage> beverages = new ArrayList<>();

  public void add(final Beverage beverage) {
    beverages.add(beverage);
  }

  public void add(final Beverage beverage, final int count) {
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

    return beverages.stream()
        .mapToInt(Beverage::getPrice)
        .sum();
  }

  public Order createOrder() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    final LocalTime currentTime = currentDateTime.toLocalTime();
    if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
      throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }

    return new Order(currentDateTime, beverages);
  }

  public Order createOrder(final LocalDateTime currentDateTime) {
    final LocalTime currentTime = currentDateTime.toLocalTime();
    if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
      throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }

    return new Order(currentDateTime, beverages);
  }

  public List<Beverage> getBeverages() {
    return beverages;
  }
}
