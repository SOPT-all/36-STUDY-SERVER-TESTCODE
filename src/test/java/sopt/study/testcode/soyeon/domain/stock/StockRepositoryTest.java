package sopt.study.testcode.soyeon.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sopt.study.testcode.soyeon.domain.product.ProductSellingStatus.HOLD;
import static sopt.study.testcode.soyeon.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.soyeon.domain.product.ProductSellingStatus.STOP_SELLING;
import static sopt.study.testcode.soyeon.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sopt.study.testcode.soyeon.domain.product.Product;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @DisplayName("상품 번호 리스트로 재고를 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // when
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001",1),
                        tuple("002", 2)
                );

    }

}