package sopt.study.testcode.kyongmin.spring.domain.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.kyongmin.spring.domain.api.contoller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.service.product.request.ProductCreateServiceRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.service.product.response.ProductResponse;
import sopt.study.testcode.kyongmin.spring.domain.product.Product;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductRepository;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
/*
* 읽기 전용 트랜잭션이 열림
* CUD작업이 동작하지 않음
* 스냅샷 저장, 변경 갑니가 동작하지 않아 단순 조회의 성능 향상
* CQRS -> commend(CUD), Query(Read)를 분리 -> 읽기가 압도적으로 요청량이 많기 때문에 따로 분리하는 경우가 많음
* 분리 작업의 시작이 바로 readOnly의 사용
* db의 분ㄴ리 -> CUD가 발생하는 마스터 db와 조회만 처리하는 slave db를 나누어 사용할 수 있음
* readOnly의 값에 따라 데베의 엔드포인트 분리 가능
* */
@Transactional(readOnly = true)
public class ProductService {
	private final ProductRepository productRepository;

	@Transactional
	public ProductResponse createProduct(ProductCreateServiceRequest request) {
		String nextProductNumber = createNextProductNumber();

		Product product = request.toEntity(nextProductNumber);
		Product savedProduct = productRepository.save(product);

		return ProductResponse.of(savedProduct);
	}

	public List<ProductResponse> getSellingProducts() {
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(ProductResponse::of)
			.collect(Collectors.toList());
	}

	private String createNextProductNumber() {
		String latestProductNumber = productRepository.findLatestProductNumber();
		if (latestProductNumber == null) {
			return "001";
		}

		int latestProductNumberInt = Integer.parseInt(latestProductNumber);
		int nextProductNumberInt = latestProductNumberInt + 1;

		return String.format("%03d", nextProductNumberInt);
	}
}
