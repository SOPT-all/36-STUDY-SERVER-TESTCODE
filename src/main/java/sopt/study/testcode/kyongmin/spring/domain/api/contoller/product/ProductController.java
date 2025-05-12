package sopt.study.testcode.kyongmin.spring.domain.api.contoller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.kyongmin.spring.domain.api.service.product.ProductService;
import sopt.study.testcode.kyongmin.spring.domain.api.service.product.response.ProductResponse;

@RestController
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@GetMapping(path = "/api/v1/products/selling")
	public List<ProductResponse> getSellingProducts() {
		return productService.getSellingProducts();
	}
}
