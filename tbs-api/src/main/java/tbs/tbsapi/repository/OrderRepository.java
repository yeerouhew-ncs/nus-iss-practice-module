package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByOrderId(Integer orderId);

    @Modifying
    @Query("UPDATE Order o SET o.orderStatus= :orderStatus WHERE o.orderId = :orderId")
    Integer updateOrderStatus(@Param("orderId") Integer orderId, @Param("orderStatus") String orderStatus);

    @Query("SELECT o FROM Order o WHERE o.eventId = :eventId")
    List<Order> findOrderByEventId(@Param("eventId") int eventId);
}
