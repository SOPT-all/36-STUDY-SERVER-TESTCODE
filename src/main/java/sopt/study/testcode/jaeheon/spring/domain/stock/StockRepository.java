package sopt.study.testcode.jaeheon.spring.domain.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
	List<Stock> findAllByProductNumberIn(List<String> productNumbers);
}
