package sopt.study.testcode.minhyuk.spring.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import sopt.study.testcode.minhyuk.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.minhyuk.spring.api.domain.order.OrderRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.orderproduct.OrderProductRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.product.Product;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductSellingType;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductType;
import sopt.study.testcode.minhyuk.spring.api.service.order.response.OrderResponse;

@ActiveProfiles("test")
@SpringBootTest

//jpa 테스트는 자동으로 trasactional 달려있어서 알아서 클랜징을 한다.
class OrderServiceTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@AfterEach
	void tearDown(){
		//하나 끝날 때마다 수해됨
		orderProductRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();


	}

	@DisplayName("주문번호 리시트를 받아 주문을 생성한다.")
	@Test
	void test(){
	    //given
		LocalDateTime registeredTime = LocalDateTime.now();
		Product product = createProduct(ProductType.HANDMADE,"001",1000);
		Product product2 = createProduct(ProductType.HANDMADE,"002",3000);
		Product product3 = createProduct(ProductType.HANDMADE,"003",5000);
		productRepository.saveAll(List.of(product,product2,product3));
		OrderCreateRequest request = OrderCreateRequest.builder().productNumber(List.of("001","002"))
			.build();
	    //when

		OrderResponse orderResponse = orderService.createOrder(request,registeredTime);


	    //then
		assertThat(orderResponse.getId()).isNotNull();
		assertThat(orderResponse).extracting("registeredDateTime","totalPrice")
			.contains(registeredTime,4000);

		assertThat(orderResponse.getProducts()).hasSize(2)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001",1000),
				tuple("002",3000)
			);


	 }
	 @DisplayName("중복되는 상품번호 리시트로 주문을 생성할 수 있다.")
	 @Test
	 void createOrderDuplicate(){
		 LocalDateTime registeredTime = LocalDateTime.now();
		 Product product = createProduct(ProductType.HANDMADE,"001",1000);
		 Product product2 = createProduct(ProductType.HANDMADE,"002",3000);
		 Product product3 = createProduct(ProductType.HANDMADE,"003",5000);
		 productRepository.saveAll(List.of(product,product2,product3));
		 OrderCreateRequest request = OrderCreateRequest.builder().productNumber(List.of("001","001"))
			 .build();
		 //when

		 OrderResponse orderResponse = orderService.createOrder(request,registeredTime);


		 //then
		 assertThat(orderResponse.getId()).isNotNull();
		 assertThat(orderResponse).extracting("registeredDateTime","totalPrice")
			 .contains(registeredTime,2000);

		 assertThat(orderResponse.getProducts()).hasSize(2)
			 .extracting("productNumber", "price")
			 .containsExactlyInAnyOrder(
				 tuple("001",1000),
				 tuple("001",1000)
			 );
	  }


	 private Product createProduct(ProductType type, String productNumber, int price){
		return Product.builder()
			.type(type)
			.productNumber(productNumber)
			.price(price)
			.selling_status(ProductSellingType.SELLING)
			.name("메뉴 이름")
			.build();

	 }
}