package sopt.study.testcode.yerin.cafekiosk.domain.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatus);

    List<Product> findAllByProductNumberIn(List<String> productNumber);

    @Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
    String findLatestProduct();
}
