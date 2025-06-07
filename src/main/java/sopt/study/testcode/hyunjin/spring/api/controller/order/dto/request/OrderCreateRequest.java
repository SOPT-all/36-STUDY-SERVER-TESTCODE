package sopt.study.testcode.hyunjin.spring.api.controller.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.hyunjin.spring.api.service.order.request.OrderCreateServiceRequest;

import java.util.List;

@Getter
public class OrderCreateRequest {
    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    @Builder
    private OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.of(this.productNumbers);
    }
}
