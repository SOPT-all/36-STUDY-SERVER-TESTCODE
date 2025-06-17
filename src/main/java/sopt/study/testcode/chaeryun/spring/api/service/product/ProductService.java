package sopt.study.testcode.chaeryun.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.study.testcode.chaeryun.spring.api.service.product.request.ProductCreateServiceRequest;
import sopt.study.testcode.chaeryun.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.chaeryun.spring.domain.product.Product;
import sopt.study.testcode.chaeryun.spring.domain.product.ProductRepository;
import sopt.study.testcode.chaeryun.spring.domain.product.ProductSellingStatus;

import java.util.List;

/**
 * readOnly = true : 읽기전용
 * CRUD 에서 CUD 동작 X / only Read
 * JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상)
 *
 * CQRS - Command / Read
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request){
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product saveProduct = productRepository.save(product);

        return ProductResponse.of(saveProduct);
    }

    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null){
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumber = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumber);
    }

    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
