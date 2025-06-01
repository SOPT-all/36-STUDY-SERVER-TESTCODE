package sopt.study.testcode.geunpyo.spring.domain.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

	List<Product> findAllByProductNumberIn(List<String> productNumbers);

	String findLatestProductNumber();
}
