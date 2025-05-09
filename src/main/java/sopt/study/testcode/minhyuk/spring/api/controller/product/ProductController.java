package sopt.study.testcode.minhyuk.spring.api.controller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.minhyuk.spring.api.service.product.ProductService;
import sopt.study.testcode.minhyuk.spring.api.service.product.response.ProductResponse;

@RequiredArgsConstructor
@RestController
public class ProductController {

	private final ProductService productService;


	@GetMapping("/api/v1/products/selling")
	public List<ProductResponse> getSellingProducts(){
		return productService.getSellingProducts();
	}
}
