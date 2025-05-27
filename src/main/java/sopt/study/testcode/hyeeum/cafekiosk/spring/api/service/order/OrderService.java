package sopt.study.testcode.hyeeum.cafekiosk.spring.api.service.order;

import org.springframework.stereotype.Service;
import sopt.study.testcode.hyeeum.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.hyeeum.cafekiosk.spring.api.service.order.response.OrderResponse;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.order.Order;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.order.OrderRepository;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.Product;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registerDateTime) {
        // 테스트를 먼저 작성한다
        List<String> productNumbers = request.getProductNumbers();

        return findProductsBy(registerDateTime, productNumbers);
    }

    private OrderResponse findProductsBy(LocalDateTime registerDateTime, List<String> productNumbers) {
        // Product
        List<Product> duplicateProducts = getProducts(productNumbers);

        // Order
        Order order = Order.create(duplicateProducts, registerDateTime);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

    private List<Product> getProducts(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers); // 중복 문제 발생
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        List<Product> duplicateProducts = productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
        return duplicateProducts;
    }
}
