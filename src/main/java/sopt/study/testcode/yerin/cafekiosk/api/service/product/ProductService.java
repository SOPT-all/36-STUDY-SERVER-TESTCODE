package sopt.study.testcode.yerin.cafekiosk.api.service.product;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.study.testcode.yerin.cafekiosk.api.service.product.response.ProductResponse;
import sopt.study.testcode.yerin.cafekiosk.domain.product.Product;
import sopt.study.testcode.yerin.cafekiosk.domain.product.ProductRepository;
import sopt.study.testcode.yerin.cafekiosk.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
