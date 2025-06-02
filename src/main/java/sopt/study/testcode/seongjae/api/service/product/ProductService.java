package sopt.study.testcode.seongjae.api.service.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.study.testcode.seongjae.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.seongjae.api.service.product.request.ProductServiceCreateRequest;
import sopt.study.testcode.seongjae.api.service.product.response.ProductResponse;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductRepository;
import sopt.study.testcode.seongjae.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public ProductResponse createProduct(final ProductServiceCreateRequest request) {
    final String nextProductNumber = createNextProductNumber();
    // nextProductNumber

    Product product = request.toEntity(nextProductNumber);
    final Product savedProduct = productRepository.save(product);

    return ProductResponse.of(savedProduct);
  }

  public List<ProductResponse> getSellingProducts() {
    final List<Product> products = productRepository.findAllBySellingStatusIn(
        ProductSellingStatus.forDisplay());

    return products.stream()
        .map(ProductResponse::of)
        .toList();
  }

  private String createNextProductNumber() {
    final String latestProductNumber = productRepository.findLatestProductNumber();
    if (latestProductNumber == null) {
      return "001";
    }

    int latestProductNumberInt = Integer.parseInt(latestProductNumber);
    int nextProductNumberInt = latestProductNumberInt + 1;

    // 9 -> 009, 10 ->010
    return String.format("%03d", nextProductNumberInt);
  }
}
