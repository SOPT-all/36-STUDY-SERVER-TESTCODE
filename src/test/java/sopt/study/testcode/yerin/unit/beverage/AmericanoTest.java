package sopt.study.testcode.yerin.unit.beverage;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import sopt.study.testcode.yerin.cafekiosk.spring.unit.beverage.Americano;

class AmericanoTest {
    @Test
    void getName() {
        Americano americano = new Americano();

        assertEquals("아메리카노", americano.getName()); // 둘이 같다고 검증 -> junit

        assertThat(americano.getName()).isEqualTo("아메리카노"); //assertJ api 사용 -> 앞으로 이거 사용, 명시적으로
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }

}
