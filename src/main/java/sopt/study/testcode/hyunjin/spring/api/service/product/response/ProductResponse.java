package sopt.study.testcode.hyunjin.spring.api.service.product.response;

import sopt.study.testcode.hyunjin.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        String productNumber,
        String type,
        String sellingStatus,
        String name,
        int price,
        LocalDateTime createTime,
        LocalDateTime modifiedTime
) {
    private static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductNumber(),
                product.getType().getText(),
                product.getSellingStatus().getText(),
                product.getName(),
                product.getPrice(),
                product.getCreateTime(),
                product.getModifiedTime()
        );
    }

    public static List<ProductResponse> from(List<Product> products) {
        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}
