package sopt.study.testcode.seongjae.api.controller.product;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.seongjae.api.ApiResponse;
import sopt.study.testcode.seongjae.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.seongjae.api.service.product.ProductService;
import sopt.study.testcode.seongjae.api.service.product.response.ProductResponse;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/api/v1/products/new")
  public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest request) {
    return  ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
  }

  @GetMapping("/api/v1/products/selling")
  public ApiResponse<List<ProductResponse>> getSellingProducts() {
    return ApiResponse.ok(productService.getSellingProducts());
  }
}
