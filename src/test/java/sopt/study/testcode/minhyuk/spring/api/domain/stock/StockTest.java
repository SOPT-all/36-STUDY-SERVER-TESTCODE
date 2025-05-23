package sopt.study.testcode.minhyuk.spring.api.domain.stock;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {
	@DisplayName("재고의 수량이 제공된 수량보다 적은지 확인한다.")
	@Test
	void test(){
	    //given
		Stock stock =Stock.create("001",1);
		int quantity = 2;


	    //when
		boolean result = stock.isQuantityLessThan(quantity);


	    //then
		assertThat(result).isTrue();
	 }

	 @DisplayName("주어진 개수만큼 재고를 차감할 수 있다.")
	 @Test
	 void deductTest(){
	     //given
		 Stock stock =Stock.create("001",1);
		 int quantity = 1;

	     //when
		 stock.deductQuantity(quantity);

	     //then
		assertThat(stock.getQuantity()).isEqualTo(0);

	  }

}