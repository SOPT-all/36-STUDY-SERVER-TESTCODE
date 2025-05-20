package sopt.study.testcode.geunpyo.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.geunpyo.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.geunpyo.spring.api.service.order.response.OrderResponse;
import sopt.study.testcode.geunpyo.spring.domain.order.Order;
import sopt.study.testcode.geunpyo.spring.domain.order.OrderRepository;
import sopt.study.testcode.geunpyo.spring.domain.product.Product;
import sopt.study.testcode.geunpyo.spring.domain.product.ProductRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> products = findProductsBy(productNumbers);

		Order order = Order.create(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);

		return OrderResponse.of(savedOrder);
	}

	private List<Product> findProductsBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

		Map<String, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p -> p));

		List<Product> duplicateProducts = productNumbers.stream()
			.map(productMap::get)
			.toList();
		return duplicateProducts;
	}
}
