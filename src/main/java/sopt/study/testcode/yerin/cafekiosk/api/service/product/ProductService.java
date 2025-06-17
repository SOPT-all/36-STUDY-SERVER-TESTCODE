package sopt.study.testcode.yerin.cafekiosk.api.service.product;

import static sopt.study.testcode.yerin.cafekiosk.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.yerin.cafekiosk.domain.product.ProductType.HANDMADE;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.study.testcode.yerin.cafekiosk.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.yerin.cafekiosk.api.controller.product.dto.request.ProductCreateServiceRequest;
import sopt.study.testcode.yerin.cafekiosk.api.service.product.response.ProductResponse;
import sopt.study.testcode.yerin.cafekiosk.domain.product.Product;
import sopt.study.testcode.yerin.cafekiosk.domain.product.ProductRepository;
import sopt.study.testcode.yerin.cafekiosk.domain.product.ProductSellingStatus;

/**
 * readOnly = true : 읽기 전용
 * CRUD 에서 CUD 동작 X / only read
 * JPA : CUD 스냅샷 저장, 변경 감지 X (성능 향상)
 * CQRS - Command / Query (책임 분리)
 *
 * **/
@Transactional(readOnly = true)
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

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProduct();
        if(latestProductNumber == null) {
            return "001";
        }
        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }
}
