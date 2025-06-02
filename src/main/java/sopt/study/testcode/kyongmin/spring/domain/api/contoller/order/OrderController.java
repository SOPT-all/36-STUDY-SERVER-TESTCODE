package sopt.study.testcode.kyongmin.spring.domain.api.contoller.order;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sopt.study.testcode.kyongmin.spring.domain.api.ApiResponse;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.request.OrderCreateRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.response.OrderResponse;
import sopt.study.testcode.kyongmin.spring.domain.api.service.order.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/api/v1/orders/new")
	public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
		LocalDateTime registeredDateTime = LocalDateTime.now();
		return ApiResponse.ok(orderService.createOrder(request, registeredDateTime));
	}
}
