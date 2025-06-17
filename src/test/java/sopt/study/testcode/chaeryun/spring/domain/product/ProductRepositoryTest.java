package sopt.study.testcode.chaeryun.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn(){
        // Given
        Product product1 = createProduct("001", ProductType.HADMADE, ProductSellingStatus.SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductType.HADMADE, ProductSellingStatus.HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", ProductType.HADMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000);

        productRepository.saveAll(List.of(product1, product2, product3));

        // When
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // Then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn(){
        // Given
        Product product1 = createProduct("001", ProductType.HADMADE, ProductSellingStatus.SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductType.HADMADE, ProductSellingStatus.HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", ProductType.HADMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000);

        productRepository.saveAll(List.of(product1, product2, product3));

        // When
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        // Then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    @Test
    void findLatestProductNumber(){
        // Given
        String targetProductNumber = "003";
        Product product1 = createProduct("001", ProductType.HADMADE, ProductSellingStatus.SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductType.HADMADE, ProductSellingStatus.HOLD, "카페라떼", 4500);
        Product product3 = createProduct(targetProductNumber, ProductType.HADMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000);

        productRepository.saveAll(List.of(product1, product2, product3));

        // When
        String latestProductNumber = productRepository.findLatestProductNumber();

        // Then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우에는 null을 반환한다.")
    @Test
    void findLatestProductNumberWhenProductIsEmpty(){
        // When
        String latestProductNumber = productRepository.findLatestProductNumber();

        // Then
        assertThat(latestProductNumber).isNull();
    }

    private static Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price){
        Product product1 = Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
        return product1;
    }
}