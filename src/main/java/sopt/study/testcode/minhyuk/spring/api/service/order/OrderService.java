package sopt.study.testcode.minhyuk.spring.api.service.order;

import static java.util.Arrays.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.minhyuk.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.minhyuk.spring.api.domain.order.Order;
import sopt.study.testcode.minhyuk.spring.api.domain.order.OrderRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.product.Product;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductRepository;
import sopt.study.testcode.minhyuk.spring.api.service.order.response.OrderResponse;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	public OrderResponse createOrder(OrderCreateRequest orderCreateRequest, LocalDateTime localDateTime) {
		List<String> productNumbers = orderCreateRequest.getProductNumber();

		//product
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap =  products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p->p));

		List<Product> productList = productNumbers.stream()
			.map(productNumber->productMap.get(productNumber))
			.collect(Collectors.toList());

		//In 절이기 때문에 중복제거가 되어서 문제가 발생한 것이다.
		//이에 대한 가공이 필요하다.


		Order order = Order.create(productList,localDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}
}
