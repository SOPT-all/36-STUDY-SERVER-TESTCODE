package sopt.study.testcode.jaeheon.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sopt.study.testcode.jaeheon.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.jaeheon.spring.api.service.product.request.ProductCreateServiceRequest;
import sopt.study.testcode.jaeheon.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.jaeheon.spring.domain.product.Product;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductRepository;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductType;
import sopt.study.testcode.jaeheon.spring.domain.product.SellingStatus;

import java.util.List;

/**
 * readOnly = true: 읽기 전용.
 * CRUD 에서 CUD 동작 X / 조회만 가능함.
 * JPA: CUD 스냅샷 저장, 변경 감지 X -> 1차 캐시 스냅샷에서 더티체킹을 안함. == 성능 향상
 *
 * CQRS - Command / Query 분리. -> 서로 연관(==책임)이 없게끔 하는 것.
 * Read 에 트래픽이 몰려서 장애가 발생할 경우 Read 때문에 Command 에 문제가 발생하지 않도록 하는 것.
 * 서비스를 쿼리용, 커맨드 형으로 나눌 수 있음. -> DB에 대한 엔드 포인트 구분이 됨. readOnly=true면 자동으로 슬레이브 DB에서 read를 하도록 설정 가능.
 *
 * 강의에서는 이걸 메서드마다 걸어주면 누락될 수 있으므로 서비스 클래스 상단에 @Transactional(readOnly = true)을 거는 방식을 추천함....
 * 그런데 이렇게 하는게 과연 얼마나 이점이 있을까? DB를 슬레이브 DB를 사용하는게 아닌 경우라면, 이게 오히려 앞 뒤로 트랜잭션을 위한 쿼리를 날려서 DB 성능 저하를 불러오지 않나????
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;


    // 여기에 Transactional을 걸어주는게 좋은건가????
    // 동시성 이슈가 있음. 여러 명이 동시에 증가하면 레이스 컨디션 발생
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request){
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);

        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(SellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}
