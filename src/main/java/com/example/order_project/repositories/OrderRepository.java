package com.example.order_project.repositories;

import com.example.order_project.enums.OrderStatus;
import com.example.order_project.models.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findOrderByOrderId(long orderId);

    Page<Order> findByUser_UserId(Long userId, Pageable pageable);
    Page<Order> findByUser_UserIdAndStatus(Long userId, OrderStatus status, Pageable pageable);
    @Modifying
    @Query("""
      update Order o
      set o.status = :status
      where o.orderId = :orderId and o.user.userId = :userId
    """)
    int updateStatusForUser(@Param("orderId") long orderId,
                            @Param("userId") long userId,
                            @Param("status") OrderStatus status);
    Page<Order> findByUser_Username(String username, Pageable pageable);
}
