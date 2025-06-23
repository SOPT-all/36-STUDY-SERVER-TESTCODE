package sopt.study.testcode.geunpyo.spring.api.controller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sopt.study.testcode.geunpyo.spring.api.ApiResponse;
import sopt.study.testcode.geunpyo.spring.api.controller.product.request.ProductCreateRequest;
import sopt.study.testcode.geunpyo.spring.api.service.product.ProductService;
import sopt.study.testcode.geunpyo.spring.api.controller.product.response.ProductResponse;

@RequiredArgsConstructor
@RestController
public class ProductController {

	private final ProductService productService;

	@PostMapping("/api/v1/products/new")
	public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
		return ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
	}

	@GetMapping("/api/v1/products/selling")
	public ApiResponse<List<ProductResponse>> getSellingProducts() {
		return ApiResponse.ok(productService.getSellingProducts());
	}

}