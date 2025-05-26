package sopt.study.testcode.jaeheon.unit;

import lombok.Getter;
import sopt.study.testcode.jaeheon.unit.beverage.Beverage;
import sopt.study.testcode.jaeheon.unit.order.Order;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10,0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22,0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage){
        beverages.add(beverage);
    }

    /**
     * 음료를 여러 잔 요청하는 경우
     * 엣지 케이스 고려 == count == 0 이면 오류 발생.
     */
    public void add(Beverage beverage, int count){
        if(count <= 0){
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
        }

        for (int i=0; i < count; ++i){
            beverages.add(beverage);
        }
    }

    public void remove(Beverage beverage){
        beverages.remove(beverage);
    }

    public void clear(){
        beverages.clear();
    }

    public int calculateTotalPrice(){
        return beverages.stream()
                .mapToInt(Beverage::getPrice)
                .sum(); // 스트림 연습할 겸 이렇게 썻지만,,, 박싱 언박싱을 진행하므로 그다지 효율적이진 않음
    }

    public Order createOrder(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();

        if(currentTime.isBefore(SHOP_OPEN_TIME)
                || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        }

        return new Order(currentDateTime, beverages);
    }


    public Order createOrder(LocalDateTime currentDateTime){
        LocalTime currentTime = currentDateTime.toLocalTime();

        if(currentTime.isBefore(SHOP_OPEN_TIME)
                || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        }

        return new Order(currentDateTime, beverages);
    }
}
