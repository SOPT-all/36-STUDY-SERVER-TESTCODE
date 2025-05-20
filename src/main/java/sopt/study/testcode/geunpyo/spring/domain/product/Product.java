package sopt.study.testcode.geunpyo.spring.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import sopt.study.testcode.geunpyo.spring.domain.BaseEntity;

@Entity
@NoArgsConstructor(access =AccessLevel.PROTECTED)
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String productNumber;

	@Enumerated(EnumType.STRING)
	private ProductType type;

	@Enumerated(EnumType.STRING)
	private ProductSellingStatus sellingStatus;

	private String name;

	private int price;

	@Builder
	private Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus,
		String name,
		int price) {
		this.productNumber = productNumber;
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public ProductType getType() {
		return type;
	}

	public ProductSellingStatus getSellingStatus() {
		return sellingStatus;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}
