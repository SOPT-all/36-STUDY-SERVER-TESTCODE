package sopt.study.testcode.yerin.cafekiosk.spring.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.registerDateTime >= :startDatefime and o.registerDateTime < :endDateTime and o.orderStatus = :orderStatus")
    List<Order> findOrdersBy(LocalDateTime startDatefime, LocalDateTime endDatefime, OrderStatus orderStatus);
}
