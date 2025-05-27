package sopt.study.testcode.kyongmin.spring.domain.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static sopt.study.testcode.kyongmin.spring.domain.product.ProductSellingStatus.*;
import static sopt.study.testcode.kyongmin.spring.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.request.OrderCreateRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.response.OrderResponse;
import sopt.study.testcode.kyongmin.spring.domain.order.OrderRepository;
import sopt.study.testcode.kyongmin.spring.domain.orderproduct.OrderProductRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.Product;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductType;
import sopt.study.testcode.kyongmin.spring.domain.stock.Stock;
import sopt.study.testcode.kyongmin.spring.domain.stock.StockRepository;

@ActiveProfiles("test")
@SpringBootTest
// @DataJpaTest -> orderservice빈이 생성되지 않음
class OrderServiceTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private OrderService orderService;

	// @Transactional의 rollback을 테스트에서 사용하지 않는 이유
	// 서비스의 트랜잭션이 이상이 없는 경우를 알 수 없음
	// 서비스에서 트랜잭션에 의한 문제가 발생해도 테스트에서는 그냥 통과해버리는 경우가 발생
	// 테스트에서 트랜잭션이 설정되며 서비스에서와 다르게 트랜잭션이 동작할 수 있기 때문
	// 아예 안쓰는 게 아니라 부작용을 인지하고 주의해서 적절하게 사용
	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		stockRepository.deleteAllInBatch();

		// orderProductRepository.deleteAll();
		// productRepository.deleteAll();
		// orderRepository.deleteAll();
	}

	@Test
	@DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
	void createOrder() {
		//given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 1000);
		Product product2 = createProduct(HANDMADE, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "002"))
			.build();

		// when
		OrderResponse response = orderService.createOrder(request, registeredDateTime);

		//then
		assertThat(response.getId()).isNotNull();
		assertThat(response)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 4000);
		assertThat(response.getProducts()).hasSize(2)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 1000),
				tuple("002", 3000)
			);
	}

	@DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
	@Test
	void createOrderWithDuplicateProductNumbers() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 1000);
		Product product2 = createProduct(HANDMADE, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "001"))
			.build();

		// when
		OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

		// then
		assertThat(orderResponse.getId()).isNotNull();
		assertThat(orderResponse)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 2000);
		assertThat(orderResponse.getProducts()).hasSize(2)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 1000),
				tuple("001", 1000)
			);
	}

	@DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
	@Test
	void createOrderWithStock() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(BOTTLE, "001", 1000);
		Product product2 = createProduct(BAKERY, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		// 일반 HANDMADE상품과 달리 재고를 차감하는 로직이 추가됨
		Stock stock1 = Stock.create("001", 2);
		Stock stock2 = Stock.create("002", 2);
		stockRepository.saveAll(List.of(stock1, stock2));

		// 중복됨 상품이 존재해도 작동하는지 함께 검증
		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "001", "002", "003"))
			.build();

		// when
		OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

		// then
		// 생성된 주문에 대해 검증
		assertThat(orderResponse.getId()).isNotNull();
		assertThat(orderResponse)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 10000);
		assertThat(orderResponse.getProducts()).hasSize(4)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 1000),
				tuple("001", 1000),
				tuple("002", 3000),
				tuple("003", 5000)
			);

		// 병음료와 베이커리의 재고에 대해 검증
		List<Stock> stocks = stockRepository.findAll();
		assertThat(stocks).hasSize(2)
			.extracting("productNumber", "quantity")
			.containsExactlyInAnyOrder(
				tuple("001", 0),
				tuple("002", 1)
			);
	}

	@DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
	@Test
	void createOrderWithNoStock() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(BOTTLE, "001", 1000);
		Product product2 = createProduct(BAKERY, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Stock stock1 = Stock.create("001", 2);
		Stock stock2 = Stock.create("002", 2);
		stock1.deductQuantity(1); // todo, 이렇게 작성하면 안된다는 걸 추후에 설명할 예정, 수량 변경을 서비스에서 호출하는데 테스트에서는 에티티가 직접 조작하면 다른 기능을 테스트하는 거니까
		stockRepository.saveAll(List.of(stock1, stock2));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "001", "002", "003"))
			.build();

		// when // then
		assertThatThrownBy(() -> orderService.createOrder(request, registeredDateTime))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("재고가 부족한 상품이 있습니다.");
	}

	// 테스트에 필요한 값만 유동적으로 생성할 수 있는 생성 메서드를 별도로 구현하는 것도 좋은 방법
	private Product createProduct(ProductType productType, String productNumber, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(productType)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}