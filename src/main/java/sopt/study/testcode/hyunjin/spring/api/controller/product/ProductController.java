package sopt.study.testcode.hyunjin.spring.api.controller.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.hyunjin.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.hyunjin.spring.api.service.product.ProductService;
import sopt.study.testcode.hyunjin.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.hyunjin.spring.common.ApiResponse;
import sopt.study.testcode.hyunjin.spring.common.ResponseBuilder;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        ProductResponse productResponse = productService.createProduct(request);
        return ResponseBuilder.created(productResponse);
    }

    @GetMapping("/api/v1/products/selling")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getSellingProducts() {
        List<ProductResponse> productResponseList = productService.getSellingProducts();
        return ResponseBuilder.ok(productResponseList);
    }


}
