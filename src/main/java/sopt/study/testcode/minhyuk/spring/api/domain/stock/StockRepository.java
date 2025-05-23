package sopt.study.testcode.minhyuk.spring.api.domain.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestPart;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
	List<Stock> findAllByProductNumberIn(List<String> productNumbers);
}
