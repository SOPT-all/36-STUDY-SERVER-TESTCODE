package sopt.study.testcode.kyongmin.spring.domain.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.request.OrderCreateRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.response.OrderResponse;
import sopt.study.testcode.kyongmin.spring.domain.order.Order;
import sopt.study.testcode.kyongmin.spring.domain.order.OrderRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.Product;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductType;
import sopt.study.testcode.kyongmin.spring.domain.stock.Stock;
import sopt.study.testcode.kyongmin.spring.domain.stock.StockRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final StockRepository stockRepository;

	// 원래 재고 감소는 동시성 처리를 해줘야함
	// 누가 먼저 온 것이냐를 판별 해줘야함 -> 낙관적 락, 비관적 락으로 보통은 구현
	public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> products = findProductsBy(productNumbers);

		deductStockQuantities(products);

		Order order = Order.create(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	// 상품 리스트를 이용하여 재고를 차감
	private void deductStockQuantities(List<Product> products) {
		// 재고 차감이 필요한 상품의 상품 번호만 추출
		List<String> stockProductNumbers = extractStockProductNumbers(products);

		// 재고에 대한 엔티티를 map으로 불러옴
		Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);
		// 차감할 재고를 map으로 카운팅
		Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

		// 재고 차감 시 중복으로 차감되는 것을 방지하기 위해 set으로 변환 후 순회
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

	// 이런 필터 같은 애들을 추출해서 명명해 주면 한단계 더 추상화되게 만드는 효과
	// 호출 순서로 나열하면 좋음
	// 상품 리스트에서 재고와 관련된 상품의 상품 번호만 추출
	private static List<String> extractStockProductNumbers(List<Product> products) {
		return products.stream()
			.filter(product -> ProductType.containsStockType(product.getType()))
			.map(Product::getProductNumber)
			.collect(Collectors.toList());
	}

	// 상품 번호를 이용하여 재고 엔티티를 조회하고 map으로 변환하여 반환
	private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
		return stocks.stream()
			.collect(Collectors.toMap(Stock::getProductNumber, s -> s));
	}

	// 재고에서 차감하기 위해 현재 주문으로 들어온 재고 관련 상품 수를 카운팅
	private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
		return stockProductNumbers.stream()
			.collect(Collectors.groupingBy(p -> p, Collectors.counting()));
	}
}
