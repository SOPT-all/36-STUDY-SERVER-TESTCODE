package sopt.study.testcode.jaeheon.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.jaeheon.spring.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.jaeheon.spring.api.controller.order.response.OrderResponse;
import sopt.study.testcode.jaeheon.spring.api.service.order.OrderService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request){
        LocalDateTime registerDateTime = LocalDateTime.now();
        return orderService.createOrder(request, registerDateTime);

    }
}
