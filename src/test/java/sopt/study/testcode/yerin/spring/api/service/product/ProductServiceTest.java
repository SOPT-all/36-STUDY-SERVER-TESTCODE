package sopt.study.testcode.yerin.spring.api.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sopt.study.testcode.yerin.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.yerin.cafekiosk.spring.api.controller.product.dto.request.ProductCreateServiceRequest;
import sopt.study.testcode.yerin.cafekiosk.spring.api.service.product.ProductService;
import sopt.study.testcode.yerin.cafekiosk.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.Product;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductRepository;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductType;

@ActiveProfiles("test")
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown(){
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근의 상품번호에서 1 증가한 것이다.")
    @Test
    void createProduct(){
        //given
        Product product = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.save(product);

        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();
        //when
        ProductResponse productResponse = productService.createProduct(request);

        //then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("002", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
                        tuple("002", HANDMADE, SELLING, "카푸치노", 5000)
                );
    }

    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
    @Test
    void createProductWhenProductIsEmpty(){
        //given
        Product product = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.save(product);

        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();
        //when
        ProductResponse productResponse = productService.createProduct(request);
        //then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("001", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(1)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", HANDMADE, SELLING, "카푸치노", 5000)
                );
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
