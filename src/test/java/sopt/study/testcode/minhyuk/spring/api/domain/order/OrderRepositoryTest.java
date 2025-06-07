package sopt.study.testcode.minhyuk.spring.api.domain.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sopt.study.testcode.minhyuk.spring.api.domain.product.Product;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductRepository;

@SpringBootTest
class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@DisplayName("결제 내역을 제대로 갖고 오는지 확인")
	@Test
	void test(){
	    //given

		Product product = Product.builder()
			.productNumber("1")
			.name("hi")
			.build();

		productRepository.save(product);

		List<Product> proList = new ArrayList<>();
		proList.add(product);

		LocalDateTime localDateTime = LocalDateTime.now();
		Order order1 = Order.create(proList,localDateTime.minusDays(1));
		order1.setOrderStatus(OrderStatus.PAYMENT_COMPLETED);
		orderRepository.save(order1);

		Order order2 = Order.create(proList,localDateTime.plusDays(1));
		order2.setOrderStatus(OrderStatus.PAYMENT_COMPLETED);
		orderRepository.save(order2);


		Order order3 = Order.create(proList,localDateTime.minusDays(2));
		order3.setOrderStatus(OrderStatus.PAYMENT_COMPLETED);
		orderRepository.save(order3);



		//when
		LocalDateTime start = localDateTime.minusDays(1);
		LocalDateTime end = localDateTime.plusDays(2);

		List<Order> orderList  = orderRepository.findOrdersByDateTime(start,end,OrderStatus.PAYMENT_COMPLETED);

	    //then
		assertThat(orderList).hasSize(2);
		assertThat(orderList).extracting(Order::getOrderStatus).containsOnly(OrderStatus.PAYMENT_COMPLETED);

	 }

}