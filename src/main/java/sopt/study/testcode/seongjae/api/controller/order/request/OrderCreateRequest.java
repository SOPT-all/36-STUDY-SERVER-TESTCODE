package sopt.study.testcode.seongjae.api.controller.order.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

  private List<String> productNumbers;

  @Builder
  private OrderCreateRequest(final List<String> productNumbers) {
    this.productNumbers = productNumbers;
  }
}
