package sopt.study.testcode.yerin.cafekiosk.api.controller.order;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.yerin.cafekiosk.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.yerin.cafekiosk.api.service.order.OrderService;
import sopt.study.testcode.yerin.cafekiosk.api.service.order.response.OrderResponse;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("api/v1/orders/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        LocalDateTime registerDateTime = LocalDateTime.now();
        return orderService.createOrder(orderCreateRequest, registerDateTime);
    }
}
