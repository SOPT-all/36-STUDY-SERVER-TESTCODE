package sopt.study.testcode.minhyuk.spring.api.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String productNumber;


	@Enumerated(EnumType.STRING)
	private ProductType type;

	@Enumerated(EnumType.STRING)
	private ProductSellingType sellingStatus;



	private String name;

	private int price;

	@Builder
	public Product(String productNumber, ProductType type, ProductSellingType selling_status, String name, int price) {
		this.productNumber = productNumber;
		this.type = type;
		this.sellingStatus = selling_status;
		this.name = name;
		this.price = price;
	}
}
