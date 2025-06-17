package sopt.study.testcode.chaeryun.unit.beverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AmericanoTest {

    @Test
    void getName(){
        Americano americano = new Americano();

//        assertEquals(americano.getName(), "아메리카노");
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    void getPrice(){
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}