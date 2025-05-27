package sopt.study.testcode.kyongmin.spring.domain.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	/*
	* SELECT * FROM PRODUCT
	* WHERE selling_status in ('SELLING', 'HOLD');
	*/
	List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

	List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
