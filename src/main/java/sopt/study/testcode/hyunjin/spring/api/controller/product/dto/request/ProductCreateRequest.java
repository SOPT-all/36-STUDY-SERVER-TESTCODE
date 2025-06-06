package sopt.study.testcode.hyunjin.spring.api.controller.product.dto.request;

import sopt.study.testcode.hyunjin.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductType;

public record ProductCreateRequest(
        ProductType type,
        ProductSellingStatus sellingStatus,
        String name,
        int price
) {
    public static ProductCreateRequest from(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return new ProductCreateRequest(
                type, sellingStatus, name, price
        );
    }
}
