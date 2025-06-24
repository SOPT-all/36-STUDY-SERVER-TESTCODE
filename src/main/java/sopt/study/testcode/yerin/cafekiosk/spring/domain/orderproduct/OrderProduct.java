package sopt.study.testcode.yerin.cafekiosk.spring.domain.orderproduct;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.BaseEntity;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.order.Order;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.Product;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public OrderProduct(Order order, Product product){
        this.order = order;
        this.product = product;
    }
}
