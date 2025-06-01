package sopt.study.testcode.geunpyo.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.geunpyo.spring.api.controller.product.response.ProductResponse;
import sopt.study.testcode.geunpyo.spring.api.service.product.request.ProductCreateServiceRequest;
import sopt.study.testcode.geunpyo.spring.domain.product.Product;
import sopt.study.testcode.geunpyo.spring.domain.product.ProductRepository;
import sopt.study.testcode.geunpyo.spring.domain.product.ProductSellingStatus;

@org.springframework.transaction.annotation.Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
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

