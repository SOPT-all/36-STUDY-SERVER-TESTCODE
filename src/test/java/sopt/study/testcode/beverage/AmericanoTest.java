package sopt.study.testcode.beverage;

import org.junit.jupiter.api.Test;
import sopt.study.testcode.hyeeum.cafekiosk.unit.beverage.Americano;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AmericanoTest {
    @Test
    void getName() {
        Americano americano = new Americano();

        //assertEquals(americano.getName(), "아메리카노"); // 이렇게 하니까 값 이상하다고 노란줄 뜸 ,, --
        assertEquals("아메리카노", americano.getName()); // 둘이 같다고 검증 -> junit

        assertThat(americano.getName()).isEqualTo("아메리카노"); //assertJ api 사용 -> 앞으로 이거 사용, 명시적으로
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }

}
