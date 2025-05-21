package sopt.study.testcode.soyeon.api.service.order;

import static sopt.study.testcode.soyeon.api.service.order.response.OrderResponse.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.study.testcode.soyeon.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.soyeon.api.service.order.response.OrderResponse;
import sopt.study.testcode.soyeon.domain.order.Order;
import sopt.study.testcode.soyeon.domain.order.OrderRepository;
import sopt.study.testcode.soyeon.domain.product.Product;
import sopt.study.testcode.soyeon.domain.product.ProductRepository;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }
}
