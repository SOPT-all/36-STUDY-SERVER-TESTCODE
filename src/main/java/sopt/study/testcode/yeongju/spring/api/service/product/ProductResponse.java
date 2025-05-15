package sopt.study.testcode.yeongju.spring.api.service.product;

import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.yeongju.spring.domain.product.Product;
import sopt.study.testcode.yeongju.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.yeongju.spring.domain.product.ProductType;

@Getter
public class ProductResponse {

    private Long id;
    private String productNumber;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    /**
     * 생성자를 그대로 쓰지 못하게 막고, 외부에서 Builder 로만 생성할 수 있다.
     */
    @Builder
    private ProductResponse(Long id, String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
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
