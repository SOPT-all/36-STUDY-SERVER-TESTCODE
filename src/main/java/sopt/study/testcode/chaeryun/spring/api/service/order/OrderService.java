package sopt.study.testcode.chaeryun.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.study.testcode.chaeryun.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.chaeryun.spring.api.service.order.response.OrderResponse;
import sopt.study.testcode.chaeryun.spring.domain.order.OrderRepository;
import sopt.study.testcode.chaeryun.spring.domain.product.Product;
import sopt.study.testcode.chaeryun.spring.domain.product.ProductRepository;
import sopt.study.testcode.chaeryun.spring.domain.order.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime){
        List<String> productNumbers = request.getProductNumbers();
        List<Product> productsBy = findProductsBy(productNumbers);

        Order order = Order.create(productsBy, registeredDateTime);
        Order save = orderRepository.save(order);

        return OrderResponse.of(save);
    }

    private List<Product> findProductsBy(List<String> productNumbers){
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> collect = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        List<Product> collect1 = productNumbers.stream()
                .map(collect::get)
                .collect(Collectors.toList());

        return collect1;
    }
}
