package sopt.study.testcode.geunpyo.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.geunpyo.spring.api.controller.product.response.ProductResponse;
import sopt.study.testcode.geunpyo.spring.domain.product.Product;
import sopt.study.testcode.geunpyo.spring.domain.product.ProductRepository;
import sopt.study.testcode.geunpyo.spring.domain.product.ProductSellingStatus;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductResponse> getSellingProducts() {
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(ProductResponse::of)
			.collect(Collectors.toList());
	}

}
