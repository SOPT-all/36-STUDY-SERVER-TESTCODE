package sopt.study.testcode.kyongmin.spring.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.kyongmin.spring.domain.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String productNumber;

	private int quantity;

	@Builder
	private Stock(String productNumber, int quantity) {
		this.productNumber = productNumber;
		this.quantity = quantity;
	}

	public static Stock create(String productNumber, int quantity) {
		return Stock.builder()
			.productNumber(productNumber)
			.quantity(quantity)
			.build();
	}

	public boolean isQuantityLessThan(int quantity) {
		return this.quantity < quantity;
	}

	// 서비스에서 이미 재고가 차감될 수 있는지 검증한 뒤에 호출되지만 수량 차감 과정에서의 기능 무결성을 보장하기 위해
	// 예외처리가 중복되거나 발생하지 않을거 같은 경우라도 해줘야함
	// 다른 곳에서 재사용하게 될 경우 예외가 발생할 수 있기 때문
	public void deductQuantity(int quantity) {
		if (isQuantityLessThan(quantity)) {
			throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");
		}

		this.quantity -= quantity;
	}
}
