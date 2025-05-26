package sopt.study.testcode.chaeryun.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.study.testcode.chaeryun.spring.domain.orderproduct.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
