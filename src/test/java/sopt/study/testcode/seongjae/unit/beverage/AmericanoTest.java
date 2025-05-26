package sopt.study.testcode.seongjae.unit.beverage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AmericanoTest {

  @Test
  void getName() {
    final Americano americano = new Americano();

    assertThat(americano.getName()).isEqualTo("아메리카노");
  }

  @Test
  void getPrice() {
    final Americano americano = new Americano();

    assertThat(americano.getPrice()).isEqualTo(4000);
  }

}