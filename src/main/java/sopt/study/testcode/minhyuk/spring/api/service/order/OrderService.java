package sopt.study.testcode.minhyuk.spring.api.service.order;

import static java.util.Arrays.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.minhyuk.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.minhyuk.spring.api.domain.order.Order;
import sopt.study.testcode.minhyuk.spring.api.domain.order.OrderRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.product.Product;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductType;
import sopt.study.testcode.minhyuk.spring.api.domain.stock.Stock;
import sopt.study.testcode.minhyuk.spring.api.domain.stock.StockRepository;
import sopt.study.testcode.minhyuk.spring.api.service.order.response.OrderResponse;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final StockRepository stockRepository;
	public OrderResponse createOrder(OrderCreateRequest orderCreateRequest, LocalDateTime localDateTime) {
		List<String> productNumbers = orderCreateRequest.getProductNumber();

		//product
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		//재고 차감이 필요한 상품들 필터링
		deducts(products);

		//In 절이기 때문에 중복제거가 되어서 문제가 발생한 것이다.
		//이에 대한 가공이 필요하다.

		Map<String, Product> productMap =  products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p->p));

		List<Product> productList = productNumbers.stream()
			.map(productNumber->productMap.get(productNumber))
			.collect(Collectors.toList());

		Order order = Order.create(productList,localDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	private void deducts(List<Product> products){
		List<String> stockProductNumbers = products.stream()
			.filter(product -> ProductType.containsStockType(product.getType()))
			.map(Product::getProductNumber)
			.toList();
		//재고 엔티티 조회
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
		Map<String,Stock> stockMap = stocks.stream()
			.collect(Collectors.toMap(Stock::getProductNumber,s->s));
		//상품별 counting
		Map<String,Long> productCountingMap = stockProductNumbers.stream().collect(
			Collectors.groupingBy(p->p,Collectors.counting())
		);
		//재고 차감 시도
		for(String stockProductNumber : new HashSet<>(stockProductNumbers)){
			Stock stock = stockMap.get(stockProductNumber);
			int quantity = productCountingMap.get(stockProductNumber).intValue();
			if(stock.isQuantityLessThan(quantity)){
				throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
			}
			stock.deductQuantity(quantity);
		}


	}
}
