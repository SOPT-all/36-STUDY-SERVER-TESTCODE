package sopt.study.testcode.geunpyo.spring.domain.order;

import static sopt.study.testcode.geunpyo.spring.domain.order.OrderStatus.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.geunpyo.spring.domain.BaseEntity;
import sopt.study.testcode.geunpyo.spring.domain.orderproduct.OrderProduct;
import sopt.study.testcode.geunpyo.spring.domain.product.Product;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	private int totalPrice;

	private LocalDateTime registeredDateTime;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderProduct> orderProduct = new ArrayList<>();

	public Order(List<Product> products, LocalDateTime registeredDateTime) {
		this.orderStatus = INIT;
		this.totalPrice = calculateTotalPrice(products);
		this.registeredDateTime = registeredDateTime;
		this.orderProduct = products.stream()
			.map(product -> new OrderProduct(this, product))
			.toList();
	}

	private int calculateTotalPrice(List<Product> products) {
		return products.stream()
			.mapToInt(Product::getPrice)
			.sum();
	}

	public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
		return new Order(products, registeredDateTime);
	}
}
