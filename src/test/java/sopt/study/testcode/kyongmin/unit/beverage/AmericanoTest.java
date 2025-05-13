package sopt.study.testcode.kyongmin.unit.beverage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import sopt.study.testcode.kyongmin.unit.beverage.Americano;

class AmericanoTest {

	@Test
	void getName() {
		Americano americano = new Americano();

		// 이건 junit의 기능
		// assertEquals("아메리카노", americano.getName());

		// 이건 assertj의 기능
		// 메서드 체이닝을 이용하여 더 풍부한 검증이 가능하고 더 명시적이어서 가독성이 좋음
		assertThat(americano.getName()).isEqualTo("아메리카노");
	}

	@Test
	void getPrice() {
		Americano americano = new Americano();

		assertThat(americano.getPrice()).isEqualTo(4000);
	}
}