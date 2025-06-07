package sopt.study.testcode.minhyuk.spring.api.service.order.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.minhyuk.spring.api.domain.order.Order;
import sopt.study.testcode.minhyuk.spring.api.domain.order.OrderStatus;
import sopt.study.testcode.minhyuk.spring.api.domain.orderproduct.OrderProduct;
import sopt.study.testcode.minhyuk.spring.api.service.product.response.ProductResponse;

@Getter
public class OrderResponse {


	private Long id;


	private OrderStatus orderStatus;

	private int totalPrice;

	private LocalDateTime registeredDateTime;

	private List<ProductResponse> products = new ArrayList<>();

	@Builder
	public OrderResponse(long id, OrderStatus orderStatus, int totalPrice, LocalDateTime registeredDateTime,
		List<ProductResponse> products) {
		this.id = id;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
		this.registeredDateTime = registeredDateTime;
		this.products = products;
	}

	public static OrderResponse of(Order order){
		return OrderResponse.builder()
			.id(order.getId())
			.totalPrice(order.getTotalPrice())
			.registeredDateTime(order.getRegisteredDateTime())
			.products(order.getOrderProducts().stream()
				.map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
				.collect(Collectors.toList()))
			.build();
	}
}
