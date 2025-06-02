package sopt.study.testcode.seongjae.api.service.order.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.seongjae.api.service.order.request.OrderCreateServiceRequest;

@Getter
public class OrderCreateServiceRequest {

  private final List<String> productNumbers;

  @Builder
  private OrderCreateServiceRequest(final List<String> productNumbers) {
    this.productNumbers = productNumbers;
  }

}
