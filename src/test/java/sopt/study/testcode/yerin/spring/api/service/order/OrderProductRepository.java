package sopt.study.testcode.yerin.spring.api.service.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.orderproduct.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
