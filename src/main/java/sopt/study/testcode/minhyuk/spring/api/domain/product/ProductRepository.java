package sopt.study.testcode.minhyuk.spring.api.domain.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

	List<Product> findAllBySellingStatusIn(List<ProductSellingType> sellingTypes);

	List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
