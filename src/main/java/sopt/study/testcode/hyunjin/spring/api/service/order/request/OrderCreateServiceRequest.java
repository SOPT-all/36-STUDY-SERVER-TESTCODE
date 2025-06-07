package sopt.study.testcode.hyunjin.spring.api.service.order.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderCreateServiceRequest(
        @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
        List<String> productNumbers
) {
    public static OrderCreateServiceRequest of(List<String> productNumbers) {
        return new OrderCreateServiceRequest(productNumbers);
    }
}
