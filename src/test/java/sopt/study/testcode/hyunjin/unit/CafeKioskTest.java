package sopt.study.testcode.hyunjin.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sopt.study.testcode.hyunjin.unit.beverage.Americano;
import sopt.study.testcode.hyunjin.unit.beverage.Latte;
import sopt.study.testcode.hyunjin.unit.common.ResponseError;
import sopt.study.testcode.hyunjin.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class CafeKioskTest {

    @Test
    @DisplayName("수동 추가 테스트 - 로그 출력용")
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    @DisplayName("음료 추가 - 1잔 추가 성공")
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("Americano");
    }

    @Test
    @DisplayName("음료 추가 - 여러 잔 추가 성공")
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
    }

    @Test
    @DisplayName("음료 추가 - 0잔 추가 시 실패")
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ResponseError.INVALID_QUANTITY.getMessage());
    }

    @Test
    @DisplayName("음료 삭제 - 1잔 삭제 성공")
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    @DisplayName("음료 초기화 - 전체 삭제 성공")
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    @DisplayName("음료 총합 가격 계산 - 계산 성공")
    void calculateTotalPrice() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        int totalPrice = cafeKiosk.calculateTotalPrice();

        assertThat(totalPrice).isEqualTo(8500);
    }

    @Test
    @DisplayName("주문 생성 - 현재 시간 기준 주문 생성 성공")
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder();

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
    }

    @Test
    @DisplayName("주문 생성 - 지정된 영업 시간 내 주문 생성 성공")
    void createOrderWithCurrentTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        Order order1 = cafeKiosk.createOrder(LocalDateTime.of(2025, 5, 5, 10, 0));
        Order order2 = cafeKiosk.createOrder(LocalDateTime.of(2025, 5, 5, 21, 59));

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
    }

    @Test
    @DisplayName("주문 생성 - 영업 시작 전 주문 생성 시 예외 발생")
    void createOrderOutsideOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2025, 5, 5, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ResponseError.OUT_OF_BUSINESS_HOURS.getMessage());
    }

    @Test
    @DisplayName("주문 생성 - 영업 종료 후 주문 생성 시 예외 발생")
    void createOrderOutsideEndTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2025, 5, 5, 22, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ResponseError.OUT_OF_BUSINESS_HOURS.getMessage());
    }
}