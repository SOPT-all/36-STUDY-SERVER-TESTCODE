package sopt.study.testcode.jaeheon.spring.api.service.product;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductRepository;

@RequiredArgsConstructor
@Component
public class ProductNumberFactory {
	private final ProductRepository productRepository;

	public String createNextProductNumber(){
		String latestProductNumber = productRepository.findLatestProductNumber();
		if(latestProductNumber == null){
			return "001";
		}

		int latestProductNumberInt = Integer.valueOf(latestProductNumber);

		return String.format("%03d", ++latestProductNumberInt);
	}
}
