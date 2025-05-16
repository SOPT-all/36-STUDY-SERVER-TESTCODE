package sopt.study.testcode.minhyuk.spring.api.controller.order;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PostExchange;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.minhyuk.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.minhyuk.spring.api.service.order.OrderService;
import sopt.study.testcode.minhyuk.spring.api.service.order.response.OrderResponse;

@RequiredArgsConstructor
@RestController
public class OrderController {

	private final OrderService orderService;

	@PostMapping("api/v1/orders/new")
	public OrderResponse createOrder(@RequestBody   OrderCreateRequest orderCreateRequest){
		LocalDateTime registeredTime = LocalDateTime.now();
		return orderService.createOrder(orderCreateRequest,registeredTime);
	}

}
