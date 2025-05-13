package sopt.study.testcode.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.Product;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.ProductRepository;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;

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
        Product product1 =
                Product.builder()
                        .productNumber("001")
                        .type(ProductType.HANDMADE)
                        .sellingStatus(SELLING)
                        .name("아메리카노")
                        .price(4000)
                        .build();

        Product product2 =
                Product.builder()
                        .productNumber("002")
                        .type(ProductType.HANDMADE)
                        .sellingStatus(ProductSellingStatus.HOLD)
                        .name("카페라떼")
                        .price(4500)
                        .build();

        Product product3 =
                Product.builder()
                        .productNumber("003")
                        .type(ProductType.HANDMADE)
                        .sellingStatus(ProductSellingStatus.STOP_SELLING)
                        .name("팥빙수")
                        .price(7000)
                        .build();
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, ProductSellingStatus.HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus") //검증하고자 하는 필드망 추출
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                ); // 순서 상관없이. 리스트 추출할 때 주로 contains~ 사용함
    }

}
