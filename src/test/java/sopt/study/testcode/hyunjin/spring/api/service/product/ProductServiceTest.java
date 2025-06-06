package sopt.study.testcode.hyunjin.spring.api.service.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sopt.study.testcode.hyunjin.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.hyunjin.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.hyunjin.spring.domain.product.Product;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductRepository;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    void createProduct() {
        // given
        Product p1 = createProduct(
                "001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000
        );
        productRepository.save(p1);
        ProductCreateRequest request = ProductCreateRequest.from(
                ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000
        );

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertThat(response)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("002", ProductType.HANDMADE.getText(), ProductSellingStatus.SELLING.getText(), "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 3000), // ✅ 가격도 3000으로
                        tuple("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                );
    }

    @Test
    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
    void createProductWhenProductIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.from(
                ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000
        );

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertThat(response)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("001", ProductType.HANDMADE.getText(), ProductSellingStatus.SELLING.getText(), "카푸치노", 5000);
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