package sopt.study.testcode.yerin.cafekiosk.spring.api.controller.order.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    @Builder
    public OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.builder()
                .productNumbers(productNumbers)
                .build();
    }
}
