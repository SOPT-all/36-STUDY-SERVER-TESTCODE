package sopt.study.testcode.jaeheon.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.jaeheon.spring.domain.product.Product;
import sopt.study.testcode.jaeheon.spring.domain.product.SellingStatus;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductType;

@Getter
public class ProductResponse {
    private Long id;
    private String productNumber;
    private ProductType type;
    private SellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductType type, SellingStatus sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse from(Product product){
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
