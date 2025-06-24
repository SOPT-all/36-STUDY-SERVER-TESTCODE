package sopt.study.testcode.yerin.cafekiosk.spring.api.controller.order.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateServiceRequest {

    private List<String> productNumbers;

    @Builder
    public OrderCreateServiceRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

}