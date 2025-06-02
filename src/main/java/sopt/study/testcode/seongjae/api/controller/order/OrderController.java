package sopt.study.testcode.seongjae.api.controller.order;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.seongjae.api.ApiResponse;
import sopt.study.testcode.seongjae.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.seongjae.api.service.order.OrderService;
import sopt.study.testcode.seongjae.api.service.order.response.OrderResponse;

@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/api/v1/orders/new")
  public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderCreateRequest request) {
    final LocalDateTime registeredDateTime = LocalDateTime.now();

    return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
  }
}
