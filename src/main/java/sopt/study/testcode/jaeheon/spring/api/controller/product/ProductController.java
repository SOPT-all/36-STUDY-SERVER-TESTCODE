package sopt.study.testcode.jaeheon.spring.api.controller.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sopt.study.testcode.jaeheon.spring.api.ApiResponse;
import sopt.study.testcode.jaeheon.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.jaeheon.spring.api.service.product.ProductService;
import sopt.study.testcode.jaeheon.spring.api.service.product.response.ProductResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest request){
        return ApiResponse.ok(productService.createProduct(request.toService()));
    }

    @GetMapping("/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts(){
        return ApiResponse.ok(productService.getSellingProducts());
    }

}
