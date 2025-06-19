package sopt.study.testcode.jaeheon.spring.api.service.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static sopt.study.testcode.jaeheon.spring.domain.product.ProductType.*;
import static sopt.study.testcode.jaeheon.spring.domain.product.SellingStatus.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import sopt.study.testcode.jaeheon.spring.IntegrationTestSupport;
import sopt.study.testcode.jaeheon.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.jaeheon.spring.api.service.product.request.ProductCreateServiceRequest;
import sopt.study.testcode.jaeheon.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.jaeheon.spring.domain.product.Product;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductRepository;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductType;
import sopt.study.testcode.jaeheon.spring.domain.product.SellingStatus;


class ProductServiceTest extends IntegrationTestSupport {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@AfterEach
	void clearInAfterTest(){
		productRepository.deleteAllInBatch();
	}

	@DisplayName("신규 상품을 등록함. 상품번호는 가장 최근 상품번호에서 1 증가한 값임.")
	@Test
	void createProduct(){
	    // given
		Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		productRepository.save(product1);

		ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
			.type(HANDMADE)
			.sellingStatus(SELLING)
			.name("카푸치노")
			.price(5000)
			.build();

	    // when
		ProductResponse response = productService.createProduct(request);

	    // then
		assertThat(response)
			.extracting("productNumber", "sellingStatus", "name")
			.contains("002",SELLING, "카푸치노");

		List<Product> products = productRepository.findAll();
		assertThat(products).hasSize(2)
			.extracting("productNumber", "type", "sellingStatus", "name", "price")
			.containsExactlyInAnyOrder(
				tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
				tuple("002", HANDMADE, SELLING, "카푸치노", 5000)
			);
	}

	@DisplayName("신규 상품 등록 시 상품이 하나도 없는 경우 상품 번호는 001임.")
	@Test
	void createProductWhenProductsIsEmpty(){
		// given
		ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
			.type(HANDMADE)
			.sellingStatus(SELLING)
			.name("카푸치노")
			.price(5000)
			.build();

		// when
		ProductResponse response = productService.createProduct(request);

		// then
		assertThat(response)
			.extracting("productNumber", "sellingStatus", "name")
			.contains("001",SELLING, "카푸치노");
		List<Product> products = productRepository.findAll();
		assertThat(products).hasSize(1)
			.extracting("productNumber", "type", "sellingStatus", "name", "price")
			.contains(
				tuple("001", HANDMADE, SELLING, "카푸치노", 5000)
			);

	}


	private Product createProduct(String productNumber, ProductType type, SellingStatus sellingStatus, String name, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(type)
			.sellingStatus(sellingStatus)
			.name(name)
			.price(price)
			.build();
	}
}