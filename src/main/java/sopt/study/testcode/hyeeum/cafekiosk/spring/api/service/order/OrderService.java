package sopt.study.testcode.hyeeum.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.study.testcode.hyeeum.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.hyeeum.cafekiosk.spring.api.service.order.response.OrderResponse;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.order.Order;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.order.OrderRepository;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.Product;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.ProductRepository;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product.ProductType;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.stock.Stock;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registerDateTime) {
        // 테스트를 먼저 작성한다
        List<String> productNumber = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumber);

        deductStockQuantities(products);

        Order order = Order.create(products, registerDateTime);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

    private void deductStockQuantities(List<Product> products) {
        // 재고 차감 체크가 필요한 상품들을 필터링
        List<String> stockProductNumbers = extractStockProductNumbers(products);

        // 재고 엔티티 조회
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        // 상품별 counting
        Map<String, Long> productCountingMap = createCountingMap(stockProductNumbers);

        // 재고 차감 시도
        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }

    private static List<String> extractStockProductNumbers(List<Product> products) {
        List<String> stockProductNumbers = products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());
        return stockProductNumbers;
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        Map<String, Stock> stockMap = stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
        return stockMap;
    }

    private static Map<String, Long> createCountingMap(List<String> stockProductNumbers) {
        Map<String, Long> productCountingMap = stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        return productCountingMap;
    }

}
