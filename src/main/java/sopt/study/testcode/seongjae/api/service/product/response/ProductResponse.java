package sopt.study.testcode.seongjae.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductSellingStatus;
import sopt.study.testcode.seongjae.domain.product.ProductType;

@Getter
public class ProductResponse {


  private final Long id;
  private final String productNumber;
  private final ProductType type;
  private final ProductSellingStatus sellingStatus;
  private final String name;
  private final int price;

  @Builder
  private ProductResponse(final Long id, final String productNumber, final ProductType type,
      final ProductSellingStatus sellingStatus, final String name, final int price) {
    this.id = id;
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public static ProductResponse of(final Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .productNumber(product.getProductNumber())
        .type(product.getType())
        .sellingStatus(product.getSellingStatus())
        .name(product.getName())
        .price(product.getPrice())
        .build();
  }
}
