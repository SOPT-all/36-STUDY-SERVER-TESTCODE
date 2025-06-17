package sopt.study.testcode.chaeryun.spring.api.service.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateServiceRequest {

    @NotEmpty(message = "상품번호 리스트 필수입니다.")
    private List<String> productNumbers;

    @Builder
    public OrderCreateServiceRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }


}
