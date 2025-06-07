package sopt.study.testcode.hyunjin.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.study.testcode.hyunjin.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.hyunjin.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.hyunjin.spring.domain.product.Product;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductRepository;
import sopt.study.testcode.hyunjin.spring.domain.product.ProductSellingStatus;

import java.util.List;


/**
 * readOnly = true : 읽기 전용
 * CRUD 에서 CUD 동작 안함 / Only Read
 * JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상)
 * <p>
 * CQRS - command / query
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    // 동시성 이슈
    // 디비에 유니크 제약을 걸고
    // 유니크에서 걸렸어 ? 겹쳤다는 거네
    // -> 그럼 3회 재시도를 자동으로 하게 함

    // 상품 번호를 등록하는 것처럼 크리티컬하지 않은 거 말고
    // 동시성 이슈가 많은 경우에는 UUID 설정을 하는 방법도 있음 - 아예 유니크한 방법으로 아이디 생성
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // productNumber
        // db 에서 마지막 저장된 Product의 상품 번호를 읽어와서 +1
        String productNumber = createNextProductNumber();
        Product product = Product.builder()
                .productNumber(productNumber)
                .type(request.type())
                .price(request.price())
                .name(request.name())
                .sellingStatus(request.sellingStatus())
                .build();

        productRepository.save(product);
        return ProductResponse.of(product);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
        return ProductResponse.from(products);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber(); // ex: "001"
        if (latestProductNumber == null) {
            return "001";
        }
        int nextNumber = Integer.parseInt(latestProductNumber) + 1;
        return String.format("%03d", nextNumber);
    }
}
