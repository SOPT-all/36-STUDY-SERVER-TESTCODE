package sopt.study.testcode.jaeheon.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sopt.study.testcode.jaeheon.spring.api.controller.order.response.OrderResponse;
import sopt.study.testcode.jaeheon.spring.api.service.order.reqeust.OrderCreateServiceRequest;
import sopt.study.testcode.jaeheon.spring.domain.order.Order;
import sopt.study.testcode.jaeheon.spring.domain.order.OrderRepository;
import sopt.study.testcode.jaeheon.spring.domain.product.Product;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductRepository;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductType;
import sopt.study.testcode.jaeheon.spring.domain.stock.Stock;
import sopt.study.testcode.jaeheon.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    /**
     * 재고 감소 == 동시성에 대한 고민이 필요함.
     * optimistic lock (낙관적, 충돌 별로 없는 경우에) / pessimistic lock (비관적, 충돌이 많은 경우에)
     */
    @Transactional
    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registerDateTime){
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

        deductStockQuantities(products);

        Order order = Order.create(products, registerDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);
    }

    private void deductStockQuantities(List<Product> products) {
        // 재고 차감 체크가 필요한 상품들을 필터링
        List<String> stockProductNumbers = extractStockProductNumbers(products);

        // 재고 엔티티 조회.
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        // 상품별 counting
        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        // 재고 차감 시도
        for (String stockProductNumber : new HashSet<>(stockProductNumbers)){
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if(stock.isQuantityLessThan(quantity)){
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }

            stock.deductQuantity(quantity);
        }
    }

    private List<String> extractStockProductNumbers(List<Product> products) {
        List<String> stockProductNumbers = products.stream()
            .filter(product -> ProductType.containsStockType(product.getType()))
            .map(Product::getProductNumber)
            .collect(Collectors.toList());
        return stockProductNumbers;
    }

    private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
            .collect(Collectors.groupingBy(productNumber -> productNumber, Collectors.counting()));
    }

    /**
     * 상품 번호별로 몇개의 요청이 들어왔는지 알 수 있음.
     * 키는 productNumber를 그대로 사용하고 그룹(productNumber)별로 개수를 세서 value로 사용하는 것.
     */
    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        Map<String, Stock> stockMap = stocks.stream()
            .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
        return stockMap;
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        List<Product> duplicateProducts = productNumbers.stream()
                .map(productNumber -> productMap.get(productNumber))
                .collect(Collectors.toList());
        return duplicateProducts;
    }
}
