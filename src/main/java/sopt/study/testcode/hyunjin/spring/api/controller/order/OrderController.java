package sopt.study.testcode.hyunjin.spring.api.controller.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.hyunjin.spring.api.controller.order.dto.request.OrderCreateRequest;
import sopt.study.testcode.hyunjin.spring.api.service.order.OrderService;
import sopt.study.testcode.hyunjin.spring.api.service.order.response.OrderResponse;
import sopt.study.testcode.hyunjin.spring.common.ApiResponse;
import sopt.study.testcode.hyunjin.spring.common.ResponseBuilder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderCreateRequest request
    ) {
        LocalDateTime now = LocalDateTime.now();
        OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), now);
        return ResponseBuilder.created(orderResponse);
    }
}
