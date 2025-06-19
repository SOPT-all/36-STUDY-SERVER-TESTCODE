package sopt.study.testcode.jaeheon.spring.api.controller.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.jaeheon.spring.api.service.order.reqeust.OrderCreateServiceRequest;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    @Builder
    private OrderCreateRequest(List<String> productNumbers){
        this.productNumbers = productNumbers;
    }

    public OrderCreateServiceRequest toServiceRequest(){
        return OrderCreateServiceRequest.builder()
            .productNumbers(productNumbers)
            .build();
    }
}
