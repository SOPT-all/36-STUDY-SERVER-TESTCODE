package sopt.study.testcode.hyunjin.spring.api.controller.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductType;

public record ProductCreateRequest(
        @NotNull(message = "상품 타입은 필수입니다.")
        ProductType type,

        @NotNull(message = "상품 판매상태는 필수입니다.")
        ProductSellingStatus sellingStatus,

        @NotBlank(message = "상품 이름은 필수입니다.")
        String name,

        @Positive(message = "상품 가격은 양수여야 합니다.")
        int price
) {
    public static ProductCreateRequest from(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return new ProductCreateRequest(
                type, sellingStatus, name, price
        );
    }
}
