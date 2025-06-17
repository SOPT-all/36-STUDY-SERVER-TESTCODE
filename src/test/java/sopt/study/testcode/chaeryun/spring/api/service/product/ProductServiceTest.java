package sopt.study.testcode.chaeryun.spring.api.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sopt.study.testcode.chaeryun.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.chaeryun.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.chaeryun.spring.domain.product.Product;
import sopt.study.testcode.chaeryun.spring.domain.product.ProductRepository;
import sopt.study.testcode.chaeryun.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.chaeryun.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown(){
        productRepository.deleteAllInBatch();
    }
    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    @Test
    void createProduct(){
        // Given
        Product product = createProduct("001", ProductType.HADMADE, ProductSellingStatus.SELLING, "아메리카노", 4000);
        productRepository.save(product);

        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HADMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        // When
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // Then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("002", ProductType.HADMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.HADMADE, ProductSellingStatus.SELLING, "아메리카노", 4000),
                        tuple("002", ProductType.HADMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                );
    }

    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
    @Test
    void createProductWhenProductIsEmpty(){
        // Given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HADMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        // When
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // Then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("001", ProductType.HADMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.HADMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                );
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