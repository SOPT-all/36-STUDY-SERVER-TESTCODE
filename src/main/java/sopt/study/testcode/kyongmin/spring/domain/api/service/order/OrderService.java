package sopt.study.testcode.kyongmin.spring.domain.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.request.OrderCreateRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.response.OrderResponse;
import sopt.study.testcode.kyongmin.spring.domain.order.Order;
import sopt.study.testcode.kyongmin.spring.domain.order.OrderRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.Product;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductRepository;

@Service
@RequiredArgsConstructor
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

		return productNumbers.stream()
			.map(productMap::get)
			.collect(Collectors.toList());
	}
}
