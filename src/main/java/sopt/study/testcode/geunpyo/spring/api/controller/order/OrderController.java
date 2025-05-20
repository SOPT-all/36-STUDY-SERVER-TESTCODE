package sopt.study.testcode.geunpyo.spring.api.controller.order;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.geunpyo.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.geunpyo.spring.api.service.order.OrderService;
import sopt.study.testcode.geunpyo.spring.api.service.order.response.OrderResponse;

@RequiredArgsConstructor
@RestController
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/api/v1/orders/new")
	public OrderResponse createOrder(OrderCreateRequest request) {
		LocalDateTime localDateTime = LocalDateTime.now();
		return orderService.createOrder(request, localDateTime);
	}
}
