package sopt.study.testcode.minhyuk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sopt.study.testcode.minhyuk.spring.api.domain.product.Product;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductSellingType;
import sopt.study.testcode.minhyuk.spring.api.domain.product.ProductType;

@Getter
public class ProductResponse {

	private Long id;
	private String productNumber;
	private ProductType type;
	private ProductSellingType selling_status;
	private String name;
	private int price;

	@Builder
	private ProductResponse(Long id, String productNumber, ProductType productType, ProductSellingType sellingType,
		String name, int price) {
		this.id = id;
		this.productNumber = productNumber;
		this.type = productType;
		this.selling_status = sellingType;
		this.name = name;
		this.price = price;
	}

	public static ProductResponse of(Product product) {
		return ProductResponse.builder()
			.id(product.getId())
			.productNumber(product.getProductNumber())
			.productType(product.getType())
			.sellingType(product.getSellingStatus())
			.name(product.getName())
			.price(product.getPrice())
			.build();
	}

}
