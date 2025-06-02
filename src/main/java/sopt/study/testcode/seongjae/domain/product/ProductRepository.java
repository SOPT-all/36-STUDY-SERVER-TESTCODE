package sopt.study.testcode.seongjae.domain.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  /**
   * select *
   * from product
   * where selling_type in ('SELLING', 'HOLD')
   *
   */
  List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

  List<Product> findAllByProductNumberIn(List<String> productNumbers);

  @Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
  String findLatestProductNumber();
}
