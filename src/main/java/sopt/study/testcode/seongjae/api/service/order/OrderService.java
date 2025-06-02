package sopt.study.testcode.seongjae.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.study.testcode.seongjae.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.seongjae.api.service.order.request.OrderCreateServiceRequest;
import sopt.study.testcode.seongjae.api.service.order.response.OrderResponse;
import sopt.study.testcode.seongjae.domain.order.Order;
import sopt.study.testcode.seongjae.domain.order.OrderRepository;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductRepository;
import sopt.study.testcode.seongjae.domain.product.ProductType;
import sopt.study.testcode.seongjae.domain.stock.Stock;
import sopt.study.testcode.seongjae.domain.stock.StockRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final StockRepository stockRepository;

  /**
   * 
   * @param request 주문 요청
   * @param registeredDateTime 주문 시간
   * @return 주문 응답
   */
  public OrderResponse createOrder(
      final OrderCreateServiceRequest request,
      final LocalDateTime registeredDateTime
  ) {
    List<String> productNumbers = request.getProductNumbers();
    // product
    final List<Product> products = findProductsBy(productNumbers);

    deductStockQuantities(products);

    // order
    Order order = Order.create(products, registeredDateTime);
    final Order savedOrder = orderRepository.save(order);

    return OrderResponse.of(savedOrder);
  }

  private void deductStockQuantities(final List<Product> products) {
    final List<String> stockProductNumbers = extractStockProductNumbers(products);

    final Map<String, Stock> stockMap = createStockMap(stockProductNumbers);

    final Map<String, Long> productContingMap = createCountingMapBy(stockProductNumbers);

    for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
      final Stock stock = stockMap.get(stockProductNumber);
      final int quantity = productContingMap.get(stockProductNumber).intValue();

      if (stock.isQuantityLessThan(quantity)) {
        throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
      }
      stock.deductQuantity(quantity);
    }
  }

  private static List<String> extractStockProductNumbers(final List<Product> products) {
    return products.stream()
        .filter(product -> ProductType.containsStockType(product.getType()))
        .map(Product::getProductNumber)
        .toList();
  }

  private Map<String, Stock> createStockMap(final List<String> stockProductNumbers) {
    final List<Stock> stocks = stockRepository.findAllByProductNumberIn(
        stockProductNumbers);
    return stocks.stream()
        .collect(Collectors.toMap(Stock::getProductNumber, stock -> stock));
  }

  private static Map<String, Long> createCountingMapBy(final List<String> stockProductNumbers) {
    return stockProductNumbers.stream()
        .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
  }

  private List<Product> findProductsBy(final List<String> productNumbers) {
    final List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
    final Map<String, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getProductNumber, p -> p));

    return productNumbers.stream()
        .map(productMap::get)
        .toList();
  }
}
