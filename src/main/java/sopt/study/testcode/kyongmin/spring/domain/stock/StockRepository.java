package sopt.study.testcode.kyongmin.spring.domain.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	List<Stock> findAllByProductNumberIn(List<String> productNumbers);
}
