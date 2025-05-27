package sopt.study.testcode.cafekiosk.spring.api.service.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.study.testcode.hyeeum.cafekiosk.spring.domain.orderproduct.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}
