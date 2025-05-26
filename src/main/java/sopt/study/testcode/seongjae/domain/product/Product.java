package sopt.study.testcode.seongjae.domain.product;

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
import sopt.study.testcode.seongjae.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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
  private Product(final String productNumber,
      final ProductType type,
      final ProductSellingStatus sellingStatus,
      final String name,
      final int price) {
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }
}
