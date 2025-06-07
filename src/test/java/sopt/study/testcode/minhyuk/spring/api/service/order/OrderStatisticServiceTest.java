package sopt.study.testcode.minhyuk.spring.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import sopt.study.testcode.minhyuk.spring.api.client.mail.MailClient;
import sopt.study.testcode.minhyuk.spring.api.domain.history.mail.MailSendHistory;
import sopt.study.testcode.minhyuk.spring.api.domain.history.mail.MailSendRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.order.Order;
import sopt.study.testcode.minhyuk.spring.api.domain.order.OrderRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.order.OrderStatus;
import sopt.study.testcode.minhyuk.spring.api.domain.orderproduct.OrderProductRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.product.Product;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductSellingType;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductType;

@SpringBootTest
class OrderStatisticServiceTest {

	@Autowired
	private OrderStatisticService statisticService;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MailSendRepository mailSendRepository;

	@MockitoBean
	private MailClient mailClient;

	@AfterEach
	void tearDown(){
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		mailSendRepository.deleteAllInBatch();

	}

	@DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
	@Test
	void test(){

	    //given
		LocalDateTime now =LocalDateTime.of(2025,6,6,4,40);

		Product product = createProduct(ProductType.HANDMADE,"001",1000);
		Product product2 = createProduct(ProductType.HANDMADE,"002",3000);
		Product product3 = createProduct(ProductType.HANDMADE,"003",5000);
		productRepository.saveAll(List.of(product,product2,product3));

		Order order1 = makeNewOrder(product, product2, product3,LocalDateTime.of(2025,6,5,23,59));


		Order order2 = makeNewOrder(product, product2, product3, now);


		Order order3 = makeNewOrder(product, product2, product3, LocalDateTime.of(2025,6,7,0,0));


		Order order4 = makeNewOrder(product, product2, product3, LocalDateTime.of(2025,6,6,23,59));


		when(mailClient.mailSend(any(String.class),any(String.class),any(String.class),any(String.class)))
			.thenReturn(true);

		//when
		boolean result = statisticService.sendOrderStatisticsMail(LocalDate.of(2025,6,6),"test@test.com");

	    //then

		assertThat(result).isTrue();

		 List<MailSendHistory> histories = mailSendRepository.findAll();
		 assertThat(histories)
			 .extracting("content")
			 .contains("총 매출 합계는 18000원입니다.");
	 }

	private  Order makeNewOrder(Product product, Product product2, Product product3, LocalDateTime now) {
		Order order = Order.builder()
			.products(List.of(product, product2, product3))
			.orderStatus(OrderStatus.PAYMENT_COMPLETED)
			.registeredDateTime(now)
			.build();
		return orderRepository.save(order);
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