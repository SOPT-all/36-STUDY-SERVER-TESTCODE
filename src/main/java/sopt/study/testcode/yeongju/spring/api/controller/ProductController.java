package sopt.study.testcode.yeongju.spring.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.yeongju.spring.api.service.product.ProductResponse;
import sopt.study.testcode.yeongju.spring.api.service.product.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/v1/products/selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }
}
