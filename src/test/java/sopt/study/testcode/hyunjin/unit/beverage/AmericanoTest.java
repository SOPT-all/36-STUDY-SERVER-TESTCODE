package sopt.study.testcode.hyunjin.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AmericanoTest {

    @Test
    @DisplayName("아메리카노 메뉴명 검증")
    void getName() {
        Americano americano = new Americano();

        // assertEquals(americano.getName(), "Americano");
        assertThat(americano.getName()).isEqualTo("Americano");
    }

    @Test
    @DisplayName("아메리카노 가격 검증")
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}