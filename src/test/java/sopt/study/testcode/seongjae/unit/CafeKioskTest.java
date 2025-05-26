package sopt.study.testcode.seongjae.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import sopt.study.testcode.seongjae.unit.beverage.Americano;
import sopt.study.testcode.seongjae.unit.beverage.Latte;
import sopt.study.testcode.seongjae.unit.order.Order;

class CafeKioskTest {

  @Test
  void add_manual_test() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());

    System.out.println(">>> 담긴 음료수: " + cafeKiosk.getBeverages().size());
    System.out.println(">>> 담긴 음료: " + cafeKiosk.getBeverages().get(0).getName());
  }

  @Test
  void add() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());

    assertThat(cafeKiosk.getBeverages()).hasSize(1);
    assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void addSeveralBeverages() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    final Americano americano = new Americano();
    cafeKiosk.add(americano, 2);

    assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
    assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
  }

  @Test
  void addZeroBeverage() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    final Americano americano = new Americano();

    assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
  }


  @Test
  void remove() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano);
    assertThat(cafeKiosk.getBeverages()).hasSize(1);

    cafeKiosk.remove(americano);
    assertThat(cafeKiosk.getBeverages()).isEmpty();
  }

  @Test
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
  void createOrder() {
    final CafeKiosk cafeKiosk = new CafeKiosk();
    final Americano americano = new Americano();

    cafeKiosk.add(americano);

    final Order order = cafeKiosk.createOrder();
    assertThat(order.getBeverages()).hasSize(1);
    assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void createOrderWithCurrentTime() {
    final CafeKiosk cafeKiosk = new CafeKiosk();
    final Americano americano = new Americano();

    cafeKiosk.add(americano);

    final Order order = cafeKiosk.createOrder(LocalDateTime.of(2025, 5, 7, 14, 0));
    assertThat(order.getBeverages()).hasSize(1);
    assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void createOrderWithOutsideOpenTime() {
    final CafeKiosk cafeKiosk = new CafeKiosk();
    final Americano americano = new Americano();

    cafeKiosk.add(americano);

    assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2025, 5, 7, 9, 59)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
  }
}