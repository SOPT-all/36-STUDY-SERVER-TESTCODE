package sopt.study.testcode.chaeryun.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.study.testcode.chaeryun.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.chaeryun.spring.domain.product.Product;
import sopt.study.testcode.chaeryun.spring.domain.product.ProductRepository;
import sopt.study.testcode.chaeryun.spring.domain.product.ProductSellingStatus;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
