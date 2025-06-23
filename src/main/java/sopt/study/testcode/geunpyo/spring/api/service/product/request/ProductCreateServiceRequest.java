package sopt.study.testcode.geunpyo.spring.api.service.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.geunpyo.spring.domain.product.Product;
import sopt.study.testcode.geunpyo.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.geunpyo.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
            .productNumber(nextProductNumber)
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
    }

}
