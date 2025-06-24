package sopt.study.testcode.yerin.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductSellingStatus.STOP_SELLING;
import static sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.Product;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductRepository;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductType;

@ActiveProfiles("test") // yml에 있는 test 프로필로 접근(sql 삽입안됨)
//@SpringBootTest // 테스트할때 서버 띄워서 테스트 가능함
@DataJpaTest // 스프링 부트 테스트 보다 가벼움. JPA관련만 빈만 로딩함
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다")
    @Test
    void test() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("001", HANDMADE, HOLD, "카페라떼", 4500);
        Product product3 = createProduct("001", HANDMADE, STOP_SELLING, "팥빙수", 7000);

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus") //검증하고자 하는 필드망 추출
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                ); // 순서 상관없이. 리스트 추출할 때 주로 contains~ 사용함
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    @Test
    void findLatestProduct() {
        // given
        String targetProductNumber = "003";

        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        Product product3 = createProduct(targetProductNumber, HANDMADE, STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
       String latestProductNumber = productRepository.findLatestProduct();

        // then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우에는 null을 반환한다.")
    @Test
    void findLatestProductWhenProductIsEmpty() {
        // when
        String latestProductNumber = productRepository.findLatestProduct();

        // then
        assertThat(latestProductNumber).isNull();
    }

    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}