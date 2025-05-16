package sopt.study.testcode.minhyuk.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.minhyuk.spring.api.domain.product.Product;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductRepository;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductSellingType;
import sopt.study.testcode.minhyuk.spring.api.service.product.response.ProductResponse;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;


	public List<ProductResponse> getSellingProducts(){
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingType.forDisplay());

		return products.stream()
			.map(product -> ProductResponse.of(product))
			.collect(Collectors.toList());
	}
}
