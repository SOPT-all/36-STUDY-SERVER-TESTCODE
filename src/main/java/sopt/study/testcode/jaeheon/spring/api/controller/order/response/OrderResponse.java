package sopt.study.testcode.jaeheon.spring.api.controller.order.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.jaeheon.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.jaeheon.spring.domain.OrderProduct.OrderProduct;
import sopt.study.testcode.jaeheon.spring.domain.order.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

    @Builder
    private OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse from(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDateTime())
                .products(order.getOrderProducts().stream()
                        .map(orderProduct -> ProductResponse.from(orderProduct.getProduct()))
                        .toList()
                )
                .build();
    }
}
