package sopt.study.testcode.minhyuk.unit.beverage;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sopt.study.testcode.minhyuk.spring.unit.beverage.Americano;

class AmericanoTest {

	@Test
	void getName(){
		Americano americano = new Americano();

		assertEquals(americano.getName(),"아메리카노");
		assertThat(americano.getName()).isEqualTo("아메리카노");
	}

	@Test
	void getPrice(){
		Americano americano =new Americano();

		assertThat(americano.getPrice()).isEqualTo(4000);
	}
}