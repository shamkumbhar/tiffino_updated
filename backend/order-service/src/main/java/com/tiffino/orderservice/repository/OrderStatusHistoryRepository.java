package com.tiffino.orderservice.repository;

import com.tiffino.orderservice.entity.OrderStatusHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory,Long> {

    List<OrderStatusHistory> findByOrderIdOrderByChangedAtAsc(Long orderId);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderStatusHistory osh WHERE osh.order.id = :orderId")
    void deleteByOrderId(Long orderId);


}
