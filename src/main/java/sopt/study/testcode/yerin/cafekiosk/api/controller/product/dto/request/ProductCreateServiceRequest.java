package sopt.study.testcode.yerin.cafekiosk.api.controller.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.yerin.cafekiosk.domain.product.Product;
import sopt.study.testcode.yerin.cafekiosk.domain.product.ProductSellingStatus;
import sopt.study.testcode.yerin.cafekiosk.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private ProductType type;

    private ProductSellingStatus sellingStatus;

    private String name;

    private int price;

    @Builder
    public ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber){
        return Product.builder()
                .productNumber(nextProductNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}