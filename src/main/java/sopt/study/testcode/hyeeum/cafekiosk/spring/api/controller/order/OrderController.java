package sopt.study.testcode.hyeeum.cafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.hyeeum.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.hyeeum.cafekiosk.spring.api.service.order.OrderService;
import sopt.study.testcode.hyeeum.cafekiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("api/v1/orders/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        LocalDateTime registerDateTime = LocalDateTime.now();
        return orderService.createOrder(request, registerDateTime);
    }
}
