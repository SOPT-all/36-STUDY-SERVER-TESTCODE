//package sopt.study.testcode.yerin;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//
//import java.time.LocalDateTime;
//import org.junit.jupiter.api.Test;
//
//class CafeKioskTest {
//    @Test //수동테스트
//    void add_manual_test() {
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        cafeKiosk.add(new Americano());
//        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
//        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
//    }
//
//    @Test
//    void add() {
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        cafeKiosk.add(new Americano());
//
//        assertThat(cafeKiosk.getBeverages()).hasSize(1);
//        // 위와 동일한 코드
//        //assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
//
//        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
//    }
//
//
//    @Test
//    void addSeveralBeverage() {
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        Americano americano = new Americano();
//
//        cafeKiosk.add(americano, 2); // 해피 케이스 - 경계값은 2가 됨
//
//        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
//        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
//    }
//
//    @Test
//    void addZeroBeverage() {
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        Americano americano = new Americano();
//
//        assertThatThrownBy(() -> cafeKiosk.add(americano, 0)) // 예외 케이스
//                .isInstanceOf(IllegalArgumentException.class) // 어떤 예외가 발생할 것인지?
//                .hasMessage("음료는 1잔 이상 주문 가능합니다."); // 메세지 검증(어떤 메세지를 던지는 예외인가?)
//    }
//
//    @Test
//    void remove() {
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        Americano americano = new Americano();
//
//        cafeKiosk.add(americano);
//        assertThat(cafeKiosk.getBeverages()).hasSize(1);
//
//        //cafeKiosk.remove(new Americano()); -> test fail
//        cafeKiosk.remove(americano);
//        assertThat(cafeKiosk.getBeverages()).isEmpty();
//    }
//
//    @Test
//    void clear() {
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        Americano americano = new Americano();
//        Latte latte = new Latte();
//
//        cafeKiosk.add(americano);
//        cafeKiosk.add(latte);
//        assertThat(cafeKiosk.getBeverages()).hasSize(2);
//
//        cafeKiosk.clear();
//        assertThat(cafeKiosk.getBeverages()).isEmpty();
//    }
//
//    @Test
//    void createOrder() {
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        Americano americano = new Americano();
//
//        cafeKiosk.add(americano);
//
//        Order order = cafeKiosk.createOrder();
//
//        assertThat(order.getBeverages()).hasSize(1);
//        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
//    }
//
//    @Test
//    void createOrderWithCurrentTime() {
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        Americano americano = new Americano();
//
//        cafeKiosk.add(americano);
//
//        Order order = cafeKiosk.createOrder(LocalDateTime.of(2025, 1, 17, 10, 0)); // 경계값 10시로 해피 테스트
//
//        assertThat(order.getBeverages()).hasSize(1);
//        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
//    }
//
//    @Test
//    void createOrderOutsideOptnTime() {
//        // 외부에서 받도록 설정.
//        // production code(Runner)에서는 현재 시간을 받도록 하고, test할 때에는 원하는 값을 넣어주는 형태로 변경함
//        CafeKiosk cafeKiosk = new CafeKiosk();
//        Americano americano = new Americano();
//
//        cafeKiosk.add(americano);
//
//        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2025, 1, 17, 9, 59)))  // 경계값 10시 -1분으로 테스트
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의바람 ㅋㅋ"); // 테스트 성공 (예외처리가 되었다는 뜻)
//    }
//}
