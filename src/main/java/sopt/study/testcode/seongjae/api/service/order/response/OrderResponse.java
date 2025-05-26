package sopt.study.testcode.seongjae.api.service.order.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.seongjae.api.service.product.response.ProductResponse;
import sopt.study.testcode.seongjae.domain.order.Order;
import sopt.study.testcode.seongjae.domain.order.OrderStatus;


@Getter
public class OrderResponse {

  private Long id;
  private OrderStatus orderStatus;
  private int totalPrice;
  private LocalDateTime registeredDateTime;
  private List<ProductResponse> products;

  @Builder
  private OrderResponse(
      final Long id,
      final OrderStatus orderStatus,
      final int totalPrice,
      final LocalDateTime registeredDateTime,
      final List<ProductResponse> products
  ) {
    this.id = id;
    this.orderStatus = orderStatus;
    this.totalPrice = totalPrice;
    this.registeredDateTime = registeredDateTime;
    this.products = products;
  }

  public static OrderResponse of(final Order order) {
    return OrderResponse.builder()
        .id(order.getId())
        .orderStatus(order.getOrderStatus())
        .totalPrice(order.getTotalPrice())
        .registeredDateTime(order.getRegisteredDateTime())
        .products(order.getOrderProducts().stream()
            .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
            .toList()
        )
        .build();
  }
}
