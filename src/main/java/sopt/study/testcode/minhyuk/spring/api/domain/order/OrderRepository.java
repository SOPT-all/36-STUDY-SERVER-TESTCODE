package sopt.study.testcode.minhyuk.spring.api.domain.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

	@Query("select o from Order o where o.registeredDateTime >= :startDateTime and o.registeredDateTime < :endDateTime and o.orderStatus = :orderStatus")
	List<Order> findOrdersByDateTime(@Param("startDateTime") LocalDateTime startDateTime,@Param("endDateTime") LocalDateTime endDateTime, @Param("orderStatus") OrderStatus orderStatus);

}
