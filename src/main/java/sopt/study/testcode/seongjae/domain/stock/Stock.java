package sopt.study.testcode.seongjae.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.seongjae.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String productNumber;

  private int quantity;

  @Builder
  protected Stock(final String productNumber, final int quantity) {
    this.productNumber = productNumber;
    this.quantity = quantity;
  }

  public static Stock create(final String productNumber, final int quantity) {
    return Stock.builder()
        .productNumber(productNumber)
        .quantity(quantity)
        .build();
  }

  public boolean isQuantityLessThan(final int quantity) {
    return this.quantity < quantity;
  }

  public void deductQuantity(final int quantity) {
    if (this.quantity < quantity) {
      throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");
    }
    this.quantity -= quantity;
  }
}
