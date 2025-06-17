package sopt.study.testcode.chaeryun.spring.api.controller.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.chaeryun.spring.api.service.order.request.OrderCreateServiceRequest;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    @NotEmpty(message = "상품번호 리스트 필수입니다.")
    private List<String> productNumbers;

    @Builder
    public OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public OrderCreateServiceRequest toServiceRequest(){
        return OrderCreateServiceRequest.builder()
                .productNumbers(this.productNumbers)
                .build();
    }
}
