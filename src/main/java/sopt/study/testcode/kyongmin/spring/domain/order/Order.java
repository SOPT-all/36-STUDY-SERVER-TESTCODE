package sopt.study.testcode.kyongmin.spring.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.kyongmin.spring.domain.BaseEntity;
import sopt.study.testcode.kyongmin.spring.domain.orderproduct.OrderProduct;
import sopt.study.testcode.kyongmin.spring.domain.product.Product;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	private int totalPrice;

	private LocalDateTime registeredDateTime;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderProduct> orderProducts = new ArrayList<>();

	@Builder
	private Order(List<Product> products, OrderStatus orderStatus, LocalDateTime registeredDateTime) {
		this.orderStatus = orderStatus;
		this.totalPrice = calculateTotalPrice(products);
		this.registeredDateTime = registeredDateTime;
		// orderProductRepository를 호출하지 않고 양방향 관계에서 cascadeType.ALL을 이용하여 생성
		this.orderProducts = products.stream()
				.map(product -> new OrderProduct(this, product))
				.collect(Collectors.toList());
	}

	public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
		return Order.builder()
				.orderStatus(OrderStatus.INIT)
				.products(products)
				.registeredDateTime(registeredDateTime)
				.build();
	}


	private int calculateTotalPrice(List<Product> products) {
		return products.stream()
				.mapToInt(Product::getPrice)
				.sum();
	}
}
