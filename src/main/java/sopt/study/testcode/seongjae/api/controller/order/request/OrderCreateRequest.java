package sopt.study.testcode.seongjae.api.controller.order.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.seongjae.api.service.order.request.OrderCreateServiceRequest;

@Getter
public class OrderCreateRequest {

  @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
  private final List<String> productNumbers;

  @Builder
  private OrderCreateRequest(final List<String> productNumbers) {
    this.productNumbers = productNumbers;
  }

  public OrderCreateServiceRequest toServiceRequest() {
    return OrderCreateServiceRequest.builder()
        .productNumbers(productNumbers)
        .build();
  }
}
