package sopt.study.testcode.seongjae.api.service.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductSellingStatus;
import sopt.study.testcode.seongjae.domain.product.ProductType;

@Builder
public record ProductServiceCreateRequest(
    ProductType type,
    ProductSellingStatus sellingStatus,
    String name,
    int price
) {

  public Product toEntity(final String nextProductNumber) {
    return Product.builder()
        .productNumber(nextProductNumber)
        .name(name)
        .type(type)
        .sellingStatus(sellingStatus)
        .price(price)
        .build();
  }
}
