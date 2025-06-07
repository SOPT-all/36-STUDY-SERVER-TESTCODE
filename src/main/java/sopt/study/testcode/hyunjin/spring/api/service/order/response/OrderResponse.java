package sopt.study.testcode.hyunjin.spring.api.service.order.response;

import sopt.study.testcode.hyunjin.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.hyunjin.spring.domain.order.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record OrderResponse(
        long id,
        int totalPrice,
        LocalDateTime registeredDateTime,
        List<ProductResponse> products
) {
    public static OrderResponse of(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalPrice(),
                order.getRegisterDateTime(),
                order.getOrderProducts().stream()
                        .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                        .collect(Collectors.toList())
        );
    }
}