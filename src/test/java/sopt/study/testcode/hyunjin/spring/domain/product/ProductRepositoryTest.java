package sopt.study.testcode.hyunjin.spring.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    void findAllBySellingStatusIn() {
        // given
        Product p1 = createProduct(
                "001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000
        );
        Product p2 = createProduct(
                "002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라뗴", 4500
        );
        Product p3 = createProduct(
                "003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000
        );

        productRepository.saveAll(List.of(p1, p2, p3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라뗴", ProductSellingStatus.HOLD)
                );
    }

    @Test
    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    void findAllByProductNumberIn() {
        // given
        Product p1 = createProduct(
                "001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000
        );
        Product p2 = createProduct(
                "002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라뗴", 4500
        );
        Product p3 = createProduct(
                "003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000
        );

        productRepository.saveAll(List.of(p1, p2, p3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라뗴", ProductSellingStatus.HOLD)
                );
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 조회한다.")
    void findLatestProductNumber() {
        // given
        Product p1 = createProduct(
                "001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000
        );
        Product p2 = createProduct(
                "002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라뗴", 4000
        );
        String targetProductNumber = "003";
        Product p3 = createProduct(
                targetProductNumber, ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "바닐라라떼", 5000
        );
        productRepository.saveAll(List.of(p1, p2, p3));

        // when
        String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 조회할 때, 상품이 하나도 없는 경우에는 null 을 반환한다..")
    void findLatestProductNumberIsEmpty() {
        // given

        // when
        String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        assertThat(latestProductNumber).isNull();
    }

    private static Product createProduct(
            String productNumber,
            ProductType type,
            ProductSellingStatus sellingStatus,
            String name,
            int price
    ) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}