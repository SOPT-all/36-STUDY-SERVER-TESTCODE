package sopt.study.testcode.jaeheon.spring.domain.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	// 테스트 스스로 작성해보기
	@Query("select o from Order o where o.registeredDateTime >= :startDateTime and o.registeredDateTime < :endDateTime and o.orderStatus = :orderStatus ")
	List<Order> findOrdersBy(LocalDateTime startDateTime, LocalDateTime endDateTime, OrderStatus orderStatus);
}
