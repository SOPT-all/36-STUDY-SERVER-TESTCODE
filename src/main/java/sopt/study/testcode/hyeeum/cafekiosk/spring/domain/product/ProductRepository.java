package sopt.study.testcode.hyeeum.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // select * from product where selling_type in ('SELLING','HOLD);  -> 와 동일함
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuss);

}
