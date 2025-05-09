package sopt.study.testcode.youngkyung.unit;


import sopt.study.testcode.youngkyung.unit.beverage.Americano;
import sopt.study.testcode.youngkyung.unit.beverage.Latte;
import sopt.study.testcode.youngkyung.unit.order.Order;

import java.time.LocalDateTime;

public class CafeKioskRunner {

    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        System.out.println(">>> 아메리카노 추가");
        cafeKiosk.add(new Latte());
        System.out.println(">>> 라떼 추가");

        int totalPrice = cafeKiosk.calculateTotalPrice();
        System.out.println("총 주문 가격 : " + totalPrice);

        Order order = cafeKiosk.createOrder(LocalDateTime.now());
    }
}
