package sopt.study.testcode.seongjae.api.controller.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import sopt.study.testcode.seongjae.api.service.product.request.ProductServiceCreateRequest;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductSellingStatus;
import sopt.study.testcode.seongjae.domain.product.ProductType;

@Builder
public record ProductCreateRequest(
    @NotNull(message = "상품 타입은 필수입니다.")
    ProductType type,

    @NotNull(message = "상품 판매 상태는 필수입니다.")
    ProductSellingStatus sellingStatus,

    @NotBlank(message = "상품 이름은 필수입니다.")
    String name,

    @Positive(message = "상품 가격은 양수여야합니다.")
    int price
) {

  public ProductServiceCreateRequest toServiceRequest() {
    return ProductServiceCreateRequest.builder()
        .name(name)
        .type(type)
        .sellingStatus(sellingStatus)
        .price(price)
        .build();
  }
}
