package sopt.study.testcode.seongjae.api.controller.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.study.testcode.seongjae.api.service.product.ProductService;
import sopt.study.testcode.seongjae.api.service.product.response.ProductResponse;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("/api/v1/products/selling")
  public List<ProductResponse> getSellingProducts() {
    return productService.getSellingProducts();
  }
}
