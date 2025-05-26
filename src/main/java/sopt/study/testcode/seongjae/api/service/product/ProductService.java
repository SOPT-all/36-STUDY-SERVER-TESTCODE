package sopt.study.testcode.seongjae.api.service.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.study.testcode.seongjae.api.service.product.response.ProductResponse;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductRepository;
import sopt.study.testcode.seongjae.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;


  public List<ProductResponse> getSellingProducts() {
    final List<Product> products = productRepository.findAllBySellingStatusIn(
        ProductSellingStatus.forDisplay());

    return products.stream()
        .map(product -> ProductResponse.of(product))
        .toList();
  }
}
